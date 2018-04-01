/*
 * Copyright 2014 by SCSK Corporation.
 * 
 * This file is part of PrimeCloud Controller(TM).
 * 
 * PrimeCloud Controller(TM) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * 
 * PrimeCloud Controller(TM) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with PrimeCloud Controller(TM). If not, see <http://www.gnu.org/licenses/>.
 */
package jp.primecloud.auto.api.instance;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import jp.primecloud.auto.api.ApiSupport;
import jp.primecloud.auto.api.ApiValidate;
import jp.primecloud.auto.api.response.address.AwsAddressResponse;
import jp.primecloud.auto.api.response.instance.AwsInstanceResponse;
import jp.primecloud.auto.api.response.instance.AzureInstanceResponse;
import jp.primecloud.auto.api.response.instance.CloudstackInstanceResponse;
import jp.primecloud.auto.api.response.instance.DescribeInstanceResponse;
import jp.primecloud.auto.api.response.instance.InstanceResponse;
import jp.primecloud.auto.api.response.instance.NiftyInstanceResponse;
import jp.primecloud.auto.api.response.instance.OpenstackInstanceResponse;
import jp.primecloud.auto.api.response.instance.VcloudInstanceNetworkResponse;
import jp.primecloud.auto.api.response.instance.VcloudInstanceResponse;
import jp.primecloud.auto.api.response.instance.VmwareInstanceResponse;
import jp.primecloud.auto.entity.crud.AwsAddress;
import jp.primecloud.auto.entity.crud.AwsInstance;
import jp.primecloud.auto.entity.crud.AzureInstance;
import jp.primecloud.auto.entity.crud.CloudstackInstance;
import jp.primecloud.auto.entity.crud.Instance;
import jp.primecloud.auto.entity.crud.NiftyInstance;
import jp.primecloud.auto.entity.crud.NiftyKeyPair;
import jp.primecloud.auto.entity.crud.OpenstackInstance;
import jp.primecloud.auto.entity.crud.Platform;
import jp.primecloud.auto.entity.crud.PlatformVcloudStorageType;
import jp.primecloud.auto.entity.crud.User;
import jp.primecloud.auto.entity.crud.VcloudInstance;
import jp.primecloud.auto.entity.crud.VcloudInstanceNetwork;
import jp.primecloud.auto.entity.crud.VmwareAddress;
import jp.primecloud.auto.entity.crud.VmwareInstance;
import jp.primecloud.auto.entity.crud.VmwareKeyPair;
import jp.primecloud.auto.exception.AutoApplicationException;
import jp.primecloud.auto.service.dto.KeyPairDto;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import com.amazonaws.services.ec2.model.Subnet;

@Path("/DescribeInstance")
public class DescribeInstance extends ApiSupport {

    /**
     * サーバ情報取得
     * 
     * @param instanceNo インスタンス番号
     * @return DescribeInstanceResponse
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DescribeInstanceResponse describeInstance(@QueryParam(PARAM_NAME_INSTANCE_NO) String instanceNo) {

        // 入力チェック
        // InstanceNo
        ApiValidate.validateInstanceNo(instanceNo);

        // サーバ情報取得
        Instance instance = getInstance(Long.parseLong(instanceNo));

        // 権限チェック
        User user = checkAndGetUser(instance);

        //プラットフォーム取得
        Platform platform = platformDao.read(instance.getPlatformNo());
        if (platform == null) {
            // プラットフォームが存在しない
            throw new AutoApplicationException("EAPI-100000", "Platform", PARAM_NAME_PLATFORM_NO,
                    instance.getPlatformNo());
        }

        //インスタンス情報設定
        InstanceResponse instanceResponse = new InstanceResponse(instance);
        // TODO CLOUD BRANCHING
        if (PLATFORM_TYPE_AWS.equals(platform.getPlatformType())) {
            //AWS
            AwsInstance awsInstance = awsInstanceDao.read(Long.parseLong(instanceNo));
            if (awsInstance == null) {
                // AWSインスタンスが存在しない
                throw new AutoApplicationException("EAPI-100000", "AwsInstance", PARAM_NAME_INSTANCE_NO, instanceNo);
            }
            AwsInstanceResponse awsResponse = new AwsInstanceResponse(awsInstance);

            //SUBNET取得
            if (StringUtils.isNotEmpty(awsInstance.getSubnetId())) {
                List<Subnet> subnets = awsDescribeService.getSubnets(user.getUserNo(), platform.getPlatformNo());
                for (Subnet subnet : subnets) {
                    if (StringUtils.equals(subnet.getSubnetId(), awsInstance.getSubnetId())) {
                        awsResponse.setSubnet(subnet.getCidrBlock());
                        break;
                    }
                }
            }

            // アドレス情報を取得
            List<AwsAddress> awsAddresses = awsAddressDao.readByUserNo(user.getUserNo());
            for (AwsAddress awsAddress : awsAddresses) {
                if (platform.getPlatformNo().equals(awsAddress.getPlatformNo())
                        && instance.getInstanceNo().equals(awsAddress.getInstanceNo())) {
                    awsResponse.setAwsAddress(new AwsAddressResponse(awsAddress));
                    break;
                }
            }

            //AWSインスタンス情報設定
            instanceResponse.setAws(awsResponse);
        } else if (PLATFORM_TYPE_CLOUDSTACK.equals(platform.getPlatformType())) {
            //CloudStack
            CloudstackInstance cloudstackInstance = cloudstackInstanceDao.read(Long.parseLong(instanceNo));
            if (cloudstackInstance == null) {
                // CloudStackインスタンスが存在しない
                throw new AutoApplicationException("EAPI-100000", "CloudstackInstance", PARAM_NAME_INSTANCE_NO,
                        instanceNo);
            }
            //CloudStackインスタンス情報設定
            instanceResponse.setCloudstack(new CloudstackInstanceResponse(cloudstackInstance));
        } else if (PLATFORM_TYPE_VMWARE.equals(platform.getPlatformType())) {
            //VMWare
            VmwareInstance vmwareInstance = vmwareInstanceDao.read(Long.parseLong(instanceNo));
            if (vmwareInstance == null) {
                // VMWareインスタンスが存在しない
                throw new AutoApplicationException("EAPI-100000", "VmwareInstance", PARAM_NAME_INSTANCE_NO, instanceNo);
            }
            VmwareInstanceResponse vmResponse = new VmwareInstanceResponse(vmwareInstance);

            //VMWARE_ADDRESS取得
            VmwareAddress vmwareAddress = vmwareAddressDao.readByInstanceNo(Long.parseLong(instanceNo));
            if (vmwareAddress != null && BooleanUtils.isTrue(vmwareAddress.getEnabled())) {
                vmResponse.setIsStaticIp(true);
                vmResponse.setSubnetMask(vmwareAddress.getSubnetMask());
                vmResponse.setDefaultGateway(vmwareAddress.getDefaultGateway());
            } else {
                vmResponse.setIsStaticIp(false);
            }

            //VMWARE_KEYPAIR取得
            List<VmwareKeyPair> keyPairs = vmwareDescribeService.getKeyPairs(user.getUserNo(),
                    instance.getPlatformNo());
            for (VmwareKeyPair keyPair : keyPairs) {
                if (keyPair.getKeyNo().equals(vmwareInstance.getKeyPairNo())) {
                    vmResponse.setKeyName(keyPair.getKeyName());
                    break;
                }
            }
            //VMWareインスタンス情報設定
            instanceResponse.setVmware(vmResponse);
        } else if (PLATFORM_TYPE_NIFTY.equals(platform.getPlatformType())) {
            //Nifty
            NiftyInstance niftyInstance = niftyInstanceDao.read(Long.parseLong(instanceNo));
            if (niftyInstance == null) {
                // /Niftyインスタンスが存在しない
                throw new AutoApplicationException("EAPI-100000", "NiftyInstance", PARAM_NAME_INSTANCE_NO, instanceNo);
            }
            NiftyInstanceResponse niftyResponse = new NiftyInstanceResponse(niftyInstance);

            //NIFTY_KEYPAIR取得
            List<NiftyKeyPair> keyPairs = niftyDescribeService.getKeyPairs(user.getUserNo(), instance.getPlatformNo());
            for (NiftyKeyPair keyPair : keyPairs) {
                if (keyPair.getKeyNo().equals(niftyInstance.getKeyPairNo())) {
                    niftyResponse.setKeyName(keyPair.getKeyName());
                    break;
                }
            }

            //Niftyインスタンス情報設定
            instanceResponse.setNifty(niftyResponse);
        } else if (PLATFORM_TYPE_VCLOUD.equals(platform.getPlatformType())) {
            //VCLOUD_INSTANCE
            VcloudInstance vcloudInstance = vcloudInstanceDao.read(Long.parseLong(instanceNo));
            if (vcloudInstance == null) {
                // VMWareインスタンスが存在しない
                throw new AutoApplicationException("EAPI-100000", "VcloudInstance", PARAM_NAME_INSTANCE_NO, instanceNo);
            }
            VcloudInstanceResponse vcloudInstanceResponse = new VcloudInstanceResponse(vcloudInstance);

            //PLATFORM_VCLOUD_STORAGE_TYPE取得
            List<PlatformVcloudStorageType> storageTypes = platformVcloudStorageTypeDao
                    .readByPlatformNo(platform.getPlatformNo());
            for (PlatformVcloudStorageType storageType : storageTypes) {
                if (storageType.getStorageTypeNo().equals(vcloudInstance.getStorageTypeNo())) {
                    vcloudInstanceResponse.setStorageTypeName(storageType.getStorageTypeName());
                    break;
                }
            }

            //VCLOUD_KEYPAIR取得
            List<KeyPairDto> keyPairs = iaasDescribeService.getKeyPairs(user.getUserNo(), instance.getPlatformNo());
            for (KeyPairDto keyPair : keyPairs) {
                if (keyPair.getKeyNo().equals(vcloudInstance.getKeyPairNo())) {
                    vcloudInstanceResponse.setKeyName(keyPair.getKeyName());
                    break;
                }
            }

            // VCloudNetwork取得
            List<VcloudInstanceNetwork> vcloudInstanceNetworks = vcloudInstanceNetworkDao
                    .readByInstanceNo(Long.parseLong(instanceNo));
            for (VcloudInstanceNetwork vcloudInstanceNetwork : vcloudInstanceNetworks) {
                // VCloudNetwork情報設定
                VcloudInstanceNetworkResponse vcloudInstanceNetworkResponse = new VcloudInstanceNetworkResponse(
                        vcloudInstanceNetwork);
                // プライマリ判定設定
                if (BooleanUtils.isTrue(vcloudInstanceNetwork.getIsPrimary())) {
                    vcloudInstanceNetworkResponse.setIsPrimary(true);
                } else {
                    vcloudInstanceNetworkResponse.setIsPrimary(false);
                }
                vcloudInstanceResponse.getVcloudNetwoks().add(vcloudInstanceNetworkResponse);
            }

            //VCloudインスタンス情報設定
            instanceResponse.setVcloud(vcloudInstanceResponse);
        } else if (PLATFORM_TYPE_OPENSTACK.equals(platform.getPlatformType())) {
            //OpenStack
            OpenstackInstance openstackInstance = openstackInstanceDao.read(Long.parseLong(instanceNo));
            if (openstackInstance == null) {
                // OpenStackインスタンスが存在しない
                throw new AutoApplicationException("EAPI-100000", "OpenstackInstance", PARAM_NAME_INSTANCE_NO,
                        instanceNo);
            }
            //OpenStackインスタンス情報設定
            instanceResponse.setOpenstack(new OpenstackInstanceResponse(openstackInstance));
        } else if (PLATFORM_TYPE_AZURE.equals(platform.getPlatformType())) {
            //Azure
            AzureInstance azureInstance = azureInstanceDao.read(Long.parseLong(instanceNo));
            if (azureInstance == null) {
                // Azureインスタンスが存在しない
                throw new AutoApplicationException("EAPI-100000", "AzureInstance", PARAM_NAME_INSTANCE_NO, instanceNo);
            }
            //Azureインスタンス情報設定
            instanceResponse.setAzure(new AzureInstanceResponse(azureInstance));
        }

        DescribeInstanceResponse response = new DescribeInstanceResponse(instanceResponse);

        return response;
    }

}
