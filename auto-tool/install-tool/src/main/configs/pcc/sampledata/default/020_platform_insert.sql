INSERT INTO PLATFORM (PLATFORM_NO, PLATFORM_NAME, PLATFORM_NAME_DISP, PLATFORM_SIMPLENAME_DISP, INTERNAL, PROXY, PLATFORM_TYPE, SELECTABLE, VIEW_ORDER) VALUES (1,'ec2_tokyo','Amazon EC2(Tokyo)','Amazon EC2(Tokyo)',true,false,'aws',1,NULL);
INSERT INTO PLATFORM_AWS (PLATFORM_NO, HOST, PORT, SECURE, EUCA, VPC, REGION, AVAILABILITY_ZONE,VPC_ID, SUBNET_ID) VALUES (1,'ap-northeast-1.amazonaws.com',443,true,false,true,NULL,NULL,NULL,NULL);
