alter table TEMPLATE_COMPONENT add constraint TEMPLATE_COMPONENT_FK1 foreign key (COMPONENT_TYPE_NO) references COMPONENT_TYPE (COMPONENT_TYPE_NO);
alter table TEMPLATE_COMPONENT add constraint TEMPLATE_COMPONENT_FK2 foreign key (TEMPLATE_NO) references TEMPLATE (TEMPLATE_NO);