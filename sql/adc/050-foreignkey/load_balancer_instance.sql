alter table LOAD_BALANCER_INSTANCE add constraint LOAD_BALANCER_INSTANCE_FK1 foreign key (LOAD_BALANCER_NO) references LOAD_BALANCER (LOAD_BALANCER_NO);
alter table LOAD_BALANCER_INSTANCE add constraint LOAD_BALANCER_INSTANCE_FK2 foreign key (INSTANCE_NO) references INSTANCE (INSTANCE_NO);
