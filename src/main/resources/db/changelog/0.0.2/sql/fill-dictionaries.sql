insert into employee_type (id, name)
values (nextval('employee_type_seq'), 'Профессорско-преподавательский состав'),
       (nextval('employee_type_seq'), 'Руководитель'),
       (nextval('employee_type_seq'), 'Вспомогательный персонал');

insert into degree (id, name)
values (nextval('degree_seq'), 'Среднее'),
       (nextval('degree_seq'), 'Среднее специальное'),
       (nextval('degree_seq'), 'Бакалавр'),
       (nextval('degree_seq'), 'Магистр'),
       (nextval('degree_seq'), 'Кандидат наук'),
       (nextval('degree_seq'), 'Доктор наук');

insert into institute (id, name)
values (nextval('institute_seq'), 'ИВТИ'),
       (nextval('institute_seq'), 'ИГВИЭ'),
       (nextval('institute_seq'), 'ИТАЭ'),
       (nextval('institute_seq'), 'ИЭВТ'),
       (nextval('institute_seq'), 'ИЭТЭ'),
       (nextval('institute_seq'), 'ИЭЭ'),
       (nextval('institute_seq'), 'ИРЭ'),
       (nextval('institute_seq'), 'ВИИ'),
       (nextval('institute_seq'), 'ИнЭИ'),
       (nextval('institute_seq'), 'ГПИ'),
       (nextval('institute_seq'), 'ИДДО'),
       (nextval('institute_seq'), 'ЭнМИ');

insert into learning_group (id, name)
values (nextval('learning_group_seq'), 'А-13б-20'),
       (nextval('learning_group_seq'), 'ИЭ-65-22'),
       (nextval('learning_group_seq'), 'ЭР-12-21');

commit;