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
package jp.primecloud.auto.api.lb;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import jp.primecloud.auto.api.ApiSupport;
import jp.primecloud.auto.api.ApiValidate;
import jp.primecloud.auto.api.response.lb.AttachLoadBalancerResponse;
import jp.primecloud.auto.common.status.InstanceStatus;
import jp.primecloud.auto.entity.crud.ComponentInstance;
import jp.primecloud.auto.entity.crud.Instance;
import jp.primecloud.auto.entity.crud.LoadBalancer;
import jp.primecloud.auto.exception.AutoApplicationException;

import org.apache.commons.lang.BooleanUtils;

@Path("/AttachLoadBalancer")
public class AttachLoadBalancer extends ApiSupport {

    /**
     * ロードバランサ サーバ割り当て有効化
     * 
     * @param loadBalancerNo ロードバランサ番号
     * @param instanceNo インスタンス番号
     * @return AttachLoadBalancerResponse
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AttachLoadBalancerResponse attachLoadBalancer(@QueryParam(PARAM_NAME_LOAD_BALANCER_NO) String loadBalancerNo,
            @QueryParam(PARAM_NAME_INSTANCE_NO) String instanceNo) {

        // 入力チェック
        // LoadBalancerNo
        ApiValidate.validateLoadBalancerNo(loadBalancerNo);
        // InstanceNo
        ApiValidate.validateInstanceNo(instanceNo);

        // ロードバランサ取得
        LoadBalancer loadBalancer = getLoadBalancer(Long.parseLong(loadBalancerNo));

        // 権限チェック
        checkAndGetUser(loadBalancer);

        // インスタンス取得
        Instance instance = getInstance(Long.parseLong(instanceNo));

        if (BooleanUtils.isFalse(instance.getFarmNo().equals(loadBalancer.getFarmNo()))) {
            //ファームとインスタンスが一致しない
            throw new AutoApplicationException("EAPI-100022", "Instance", loadBalancer.getFarmNo(),
                    PARAM_NAME_INSTANCE_NO, instanceNo);
        }

        // サーバのステータスチェック
        InstanceStatus instanceStatus = InstanceStatus.fromStatus(instance.getStatus());
        if (instanceStatus == InstanceStatus.CONFIGURING || instanceStatus == InstanceStatus.WARNING) {
            // ステータスが Configuring 及び Warning の場合は割り当て不可
            throw new AutoApplicationException("EAPI-100001", loadBalancerNo, instanceNo);
        }

        // コンポーネントのチェック
        List<ComponentInstance> componentInstances = componentInstanceDao
                .readByComponentNo(loadBalancer.getComponentNo());
        boolean isContain = false;
        for (ComponentInstance componentInstance : componentInstances) {
            if (BooleanUtils.isFalse(componentInstance.getAssociate())) {
                continue;
            }
            if (componentInstance.getInstanceNo().equals(instance.getInstanceNo())) {
                isContain = true;
                break;
            }
        }
        if (BooleanUtils.isFalse(isContain)) {
            // インスタンスがコンポーネントに含まれていない場合
            throw new AutoApplicationException("EAPI-100012", Long.parseLong(loadBalancerNo),
                    loadBalancer.getComponentNo(), Long.parseLong(instanceNo));
        }

        // ロードバランサ サーバ割り当て有効化設定処理
        List<Long> instanceNos = new ArrayList<Long>();
        instanceNos.add(Long.parseLong(instanceNo));
        loadBalancerService.enableInstances(Long.parseLong(loadBalancerNo), instanceNos);

        AttachLoadBalancerResponse response = new AttachLoadBalancerResponse();

        return response;
    }

}
