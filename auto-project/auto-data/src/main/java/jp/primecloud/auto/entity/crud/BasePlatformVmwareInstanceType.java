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
package jp.primecloud.auto.entity.crud;

import java.io.Serializable;

/**
 * <p>
 * PLATFORM_VMWARE_INSTANCE_TYPEに対応したエンティティのベースクラスです。
 * </p>
 *
 */
public abstract class BasePlatformVmwareInstanceType implements Serializable {

    /** SerialVersionUID */
    private static final long serialVersionUID = 1L;

    /** PLATFORM_NO [BIGINT(19,0)] */
    private Long platformNo;

    /** INSTANCE_TYPE_NAME [VARCHAR(100,0)] */
    private String instanceTypeName;

    /** CPU [INT(10,0)] */
    private Integer cpu;

    /** MEMORY [BIGINT(19,0)] */
    private Long memory;

    /**
     * platformNoを取得します。
     *
     * @return platformNo
     */
    public Long getPlatformNo() {
        return platformNo;
    }

    /**
     * platformNoを設定します。
     *
     * @param platformNo platformNo
     */
    public void setPlatformNo(Long platformNo) {
        this.platformNo = platformNo;
    }

    /**
     * instanceTypeNameを取得します。
     *
     * @return instanceTypeName
     */
    public String getInstanceTypeName() {
        return instanceTypeName;
    }

    /**
     * instanceTypeNameを設定します。
     *
     * @param instanceTypeName instanceTypeName
     */
    public void setInstanceTypeName(String instanceTypeName) {
        this.instanceTypeName = instanceTypeName;
    }

    /**
     * cpuを取得します。
     *
     * @return cpu
     */
    public Integer getCpu() {
        return cpu;
    }

    /**
     * cpuを設定します。
     *
     * @param cpu cpu
     */
    public void setCpu(Integer cpu) {
        this.cpu = cpu;
    }

    /**
     * memoryを取得します。
     *
     * @return memory
     */
    public Long getMemory() {
        return memory;
    }

    /**
     * memoryを設定します。
     *
     * @param memory memory
     */
    public void setMemory(Long memory) {
        this.memory = memory;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = 1;

        final int prime = 31;
        result = prime * result + ((platformNo == null) ? 0 : platformNo.hashCode());
        result = prime * result + ((instanceTypeName == null) ? 0 : instanceTypeName.hashCode());
        result = prime * result + ((cpu == null) ? 0 : cpu.hashCode());
        result = prime * result + ((memory == null) ? 0 : memory.hashCode());

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }

        final BasePlatformVmwareInstanceType other = (BasePlatformVmwareInstanceType) obj;
        if (platformNo == null) {
            if (other.platformNo != null) { return false; }
        } else if (!platformNo.equals(other.platformNo)) {
            return false;
        }
        if (instanceTypeName == null) {
            if (other.instanceTypeName != null) { return false; }
        } else if (!instanceTypeName.equals(other.instanceTypeName)) {
            return false;
        }
        if (cpu == null) {
            if (other.cpu != null) { return false; }
        } else if (!cpu.equals(other.cpu)) {
            return false;
        }
        if (memory == null) {
            if (other.memory != null) { return false; }
        } else if (!memory.equals(other.memory)) {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("PlatformVmwareInstanceType").append(" [");
        sb.append("platformNo=").append(platformNo).append(", ");
        sb.append("instanceTypeName=").append(instanceTypeName).append(", ");
        sb.append("cpu=").append(cpu).append(", ");
        sb.append("memory=").append(memory);
        sb.append("]");
        return sb.toString();
    }

}
