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
package jp.primecloud.auto.ui;

import jp.primecloud.auto.common.constant.PCCConstant;
import jp.primecloud.auto.service.AwsDescribeService;
import jp.primecloud.auto.service.IaasDescribeService;
import jp.primecloud.auto.service.dto.InstanceDto;
import jp.primecloud.auto.ui.util.BeanContext;
import jp.primecloud.auto.ui.util.Icons;
import jp.primecloud.auto.ui.util.ViewMessages;
import jp.primecloud.auto.ui.util.ViewProperties;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * <p>
 * Windowsサーバのパスワード取得画面を生成します。
 * </p>
 *
 */
@SuppressWarnings("serial")
public class WinPassword extends Window {

    private InstanceDto instance;

    private TextField passwordField;

    private TextField privateKeyField;

    public WinPassword(InstanceDto instance) {
        this.instance = instance;
    }

    @Override
    public void attach() {
        setCaption(ViewProperties.getCaption("description.getPassword"));
        setModal(true);
        setWidth("580px");
        setIcon(Icons.EDITMINI.resource());

        VerticalLayout layout = (VerticalLayout) getContent();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setStyleName("win-password");

        //プラットフォーム
        String keyName = "";
        if (PCCConstant.PLATFORM_TYPE_AWS.equals(instance.getPlatform().getPlatform().getPlatformType())) {
            keyName = instance.getAwsInstance().getKeyName();
        } else if (PCCConstant.PLATFORM_TYPE_CLOUDSTACK
                .equals(instance.getPlatform().getPlatform().getPlatformType())) {
            keyName = instance.getCloudstackInstance().getKeyName();
        }

        Label privateKeyLabel = new Label(ViewMessages.getMessage("IUI-000099", keyName));
        layout.addComponent(privateKeyLabel);

        privateKeyField = new TextField();
        privateKeyField.setSizeFull();
        privateKeyField.setRows(10);
        privateKeyField.setStyleName("privatekey");
        layout.addComponent(privateKeyField);

        passwordField = new TextField("");
        passwordField.setColumns(20);
        passwordField.setCaption(ViewProperties.getCaption("label.password"));
        passwordField.setStyleName("password");
        layout.addComponent(passwordField);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setMargin(true);
        horizontalLayout.setSpacing(true);

        Button passwordButton = new Button(ViewProperties.getCaption("button.getPassword"));
        passwordButton.addListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                getPassword(instance.getInstance().getInstanceNo());
            }
        });
        horizontalLayout.addComponent(passwordButton);

        Button closeButton = new Button(ViewProperties.getCaption("button.close"));
        closeButton.addListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                close();
            }
        });
        horizontalLayout.addComponent(closeButton);

        layout.addComponent(horizontalLayout);
        layout.setComponentAlignment(horizontalLayout, Alignment.BOTTOM_RIGHT);
        layout.setComponentAlignment(passwordField, Alignment.MIDDLE_CENTER);

        initValidation();
    }

    private void initValidation() {
        String message = ViewMessages.getMessage("IUI-000097");
        privateKeyField.setRequired(true);
        privateKeyField.setRequiredError(message);
    }

    private void getPassword(Long instanceNo) {
        privateKeyField.validate();

        String privateKey = privateKeyField.getValue().toString();

        // パスワードを取得
        String password = null;
        if (PCCConstant.PLATFORM_TYPE_AWS.equals(instance.getPlatform().getPlatform().getPlatformType())) {
            // AWSの場合
            AwsDescribeService awsDescribeService = BeanContext.getBean(AwsDescribeService.class);
            password = awsDescribeService.getPassword(instanceNo, privateKey);
        } else if (PCCConstant.PLATFORM_TYPE_CLOUDSTACK
                .equals(instance.getPlatform().getPlatform().getPlatformType())) {
            // CloudStackの場合
            IaasDescribeService iaasDescribeService = BeanContext.getBean(IaasDescribeService.class);
            password = iaasDescribeService.getPassword(instanceNo, privateKey);
        }

        passwordField.setValue(password);
    }

}
