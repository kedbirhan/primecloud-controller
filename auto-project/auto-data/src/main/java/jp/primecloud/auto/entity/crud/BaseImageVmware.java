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
 * IMAGE_VMWAREに対応したエンティティのベースクラスです。
 * </p>
 *
 */
public abstract class BaseImageVmware implements Serializable {

    /** SerialVersionUID */
    private static final long serialVersionUID = 1L;

    /** IMAGE_NO [BIGINT(19,0)] */
    private Long imageNo;

    /** TEMPLATE_NAME [VARCHAR(100,0)] */
    private String templateName;

    /** INSTANCE_TYPES [VARCHAR(500,0)] */
    private String instanceTypes;

    /** ROOT_SIZE [INT(10,0)] */
    private Integer rootSize;

    /** DEFAULT_INSTANCE_TYPE [VARCHAR(30,0)] */
    private String defaultInstanceType;

    /**
     * imageNoを取得します。
     *
     * @return imageNo
     */
    public Long getImageNo() {
        return imageNo;
    }

    /**
     * imageNoを設定します。
     *
     * @param imageNo imageNo
     */
    public void setImageNo(Long imageNo) {
        this.imageNo = imageNo;
    }

    /**
     * templateNameを取得します。
     *
     * @return templateName
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * templateNameを設定します。
     *
     * @param templateName templateName
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * instanceTypesを取得します。
     *
     * @return instanceTypes
     */
    public String getInstanceTypes() {
        return instanceTypes;
    }

    /**
     * instanceTypesを設定します。
     *
     * @param instanceTypes instanceTypes
     */
    public void setInstanceTypes(String instanceTypes) {
        this.instanceTypes = instanceTypes;
    }

    /**
     * rootSizeを取得します。
     *
     * @return rootSize
     */
    public Integer getRootSize() {
        return rootSize;
    }

    /**
     * rootSizeを設定します。
     *
     * @param rootSize rootSize
     */
    public void setRootSize(Integer rootSize) {
        this.rootSize = rootSize;
    }

    /**
     * defaultInstanceTypeを取得します。
     *
     * @return defaultInstanceType
     */
    public String getDefaultInstanceType() {
        return defaultInstanceType;
    }

    /**
     * defaultInstanceTypeを設定します。
     *
     * @param defaultInstanceType defaultInstanceType
     */
    public void setDefaultInstanceType(String defaultInstanceType) {
        this.defaultInstanceType = defaultInstanceType;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = 1;

        final int prime = 31;
        result = prime * result + ((imageNo == null) ? 0 : imageNo.hashCode());
        result = prime * result + ((templateName == null) ? 0 : templateName.hashCode());
        result = prime * result + ((instanceTypes == null) ? 0 : instanceTypes.hashCode());
        result = prime * result + ((rootSize == null) ? 0 : rootSize.hashCode());
        result = prime * result + ((defaultInstanceType == null) ? 0 : defaultInstanceType.hashCode());

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

        final BaseImageVmware other = (BaseImageVmware) obj;
        if (imageNo == null) {
            if (other.imageNo != null) { return false; }
        } else if (!imageNo.equals(other.imageNo)) {
            return false;
        }
        if (templateName == null) {
            if (other.templateName != null) { return false; }
        } else if (!templateName.equals(other.templateName)) {
            return false;
        }
        if (instanceTypes == null) {
            if (other.instanceTypes != null) { return false; }
        } else if (!instanceTypes.equals(other.instanceTypes)) {
            return false;
        }
        if (rootSize == null) {
            if (other.rootSize != null) { return false; }
        } else if (!rootSize.equals(other.rootSize)) {
            return false;
        }
        if (defaultInstanceType == null) {
            if (other.defaultInstanceType != null) { return false; }
        } else if (!defaultInstanceType.equals(other.defaultInstanceType)) {
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
        sb.append("ImageVmware").append(" [");
        sb.append("imageNo=").append(imageNo).append(", ");
        sb.append("templateName=").append(templateName).append(", ");
        sb.append("instanceTypes=").append(instanceTypes).append(", ");
        sb.append("rootSize=").append(rootSize).append(", ");
        sb.append("defaultInstanceType=").append(defaultInstanceType);
        sb.append("]");
        return sb.toString();
    }

}
