INSERT INTO IMAGE (IMAGE_NO, PLATFORM_NO, IMAGE_NAME, IMAGE_NAME_DISP, OS, OS_DISP, SELECTABLE, COMPONENT_TYPE_NOS, ZABBIX_TEMPLATE, ZABBIX_DISABLED, PUPPET_DISABLED, VIEW_ORDER) VALUES (100, 1,'application','アプリケーション開発基盤','centos65','CentOS 6.5',true,'1,2,4','Template_OS_Linux',false,false,NULL);
INSERT INTO IMAGE (IMAGE_NO, PLATFORM_NO, IMAGE_NAME, IMAGE_NAME_DISP, OS, OS_DISP, SELECTABLE, COMPONENT_TYPE_NOS, ZABBIX_TEMPLATE, ZABBIX_DISABLED, PUPPET_DISABLED, VIEW_ORDER) VALUES (200, 1,'linux_amazon_20161028','Amazon Linux AMI','amazon-linux','Amazon Linux (2016.09.0)',true,'','Template_OS_Linux',false,true,NULL);
INSERT INTO IMAGE (IMAGE_NO, PLATFORM_NO, IMAGE_NAME, IMAGE_NAME_DISP, OS, OS_DISP, SELECTABLE, COMPONENT_TYPE_NOS, ZABBIX_TEMPLATE, ZABBIX_DISABLED, PUPPET_DISABLED, VIEW_ORDER) VALUES (300, 1,'windows_2016_20161123','Windows Server 2016','windows-2016','Windows Server 2016',true,'',null,true,true,NULL);
INSERT INTO IMAGE_AWS (IMAGE_NO, IMAGE_ID, KERNEL_ID, RAMDISK_ID, INSTANCE_TYPES, EBS_IMAGE, ROOT_SIZE) VALUES (100, 'ami-96904396','aki-176bf516',NULL,'t1.micro,m1.small,m1.medium,m1.large,m1.xlarge',true,NULL);
INSERT INTO IMAGE_AWS (IMAGE_NO, IMAGE_ID, KERNEL_ID, RAMDISK_ID, INSTANCE_TYPES, EBS_IMAGE, ROOT_SIZE) VALUES (200, 'ami-0c11b26d',NULL,NULL,'t2.nano,t2.micro,t2.small,t2.medium,t2.large',true,NULL);
INSERT INTO IMAGE_AWS (IMAGE_NO, IMAGE_ID, KERNEL_ID, RAMDISK_ID, INSTANCE_TYPES, EBS_IMAGE, ROOT_SIZE) VALUES (300, 'ami-c2c574a3',NULL,NULL,'t2.small,t2.medium,t2.large',true,NULL);
