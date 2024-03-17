drop sequence if exists award_seq;
drop sequence if exists degree_seq;
drop sequence if exists department_seq;
drop sequence if exists discipline_seq;
drop sequence if exists education_document_seq;
drop sequence if exists employee_job_seq;
drop sequence if exists employee_seq;
drop sequence if exists employee_type_seq;
drop sequence if exists institute_seq;
drop sequence if exists learning_group_seq;
drop sequence if exists lesson_seq;

create sequence award_seq start with 1 increment by 1;
create sequence degree_seq start with 1 increment by 1;
create sequence department_seq start with 1 increment by 1;
create sequence discipline_seq start with 1 increment by 1;
create sequence education_document_seq start with 1 increment by 1;
create sequence employee_job_seq start with 1 increment by 1;
create sequence employee_seq start with 1 increment by 1;
create sequence employee_type_seq start with 1 increment by 1;
create sequence institute_seq start with 1 increment by 1;
create sequence learning_group_seq start with 1 increment by 1;
create sequence lesson_seq start with 1 increment by 1;

drop table if exists award;
drop table if exists degree;
drop table if exists department;
drop table if exists discipline;
drop table if exists education_document;
drop table if exists employee;
drop table if exists employee_department;
drop table if exists employee_job;
drop table if exists employee_type;
drop table if exists institute;
drop table if exists learning_group;
drop table if exists lesson;
drop table if exists employee_discipline;

create table award
(
    date_recieve       date,
    employee_id        bigint        not null,
    id                 bigint        not null,
    giver_organization varchar(512)  not null,
    text               varchar(2000) not null,
    primary key (id)
);
create table degree
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
);
create table department
(
    foundation_year integer,
    id              bigint not null,
    institute_id    bigint,
    room            varchar(10),
    name            varchar(255),
    primary key (id)
);
create table discipline
(
    department_id bigint,
    id            bigint not null,
    name          varchar(255),
    primary key (id)
);
create table education_document
(
    giving_date     date        not null,
    employee_id     bigint      not null,
    id              bigint      not null,
    number          varchar(15) not null,
    serie           varchar(15) not null,
    university_name varchar(255),
    primary key (id)
);
create table employee
(
    date_of_birth         date         not null,
    emp_record_date_start date,
    is_active             boolean      not null,
    is_emp_record_digital boolean      not null,
    passport_serie        varchar(4)   not null,
    passport_number       varchar(6)   not null,
    passport_dep_code     varchar(7)   not null,
    degree_id             bigint,
    employee_type_id      bigint       not null,
    id                    bigint       not null,
    emp_record_num        varchar(20)  not null,
    passport_giver_organ  varchar(127) not null,
    registration_address  varchar(300) not null,
    fio                   varchar(255) not null,
    contract_num          varchar(30)  not null,
    contract_date         date         not null,
    contract_expire_date  date         not null,
    rank                  varchar(30),
    primary key (id)
);
create table employee_department
(
    department_id bigint not null,
    employee_id   bigint not null
);
create table employee_job
(
    date_end          date,
    date_start        date           not null,
    salary            numeric(38, 2) not null,
    employee_id       bigint         not null,
    id                bigint         not null,
    position          varchar(127)   not null,
    organization_name varchar(255)   not null,
    primary key (id)
);
create table employee_type
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
);
create table institute
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
);
create table learning_group
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
);
create table lesson
(
    date_end      timestamp(6),
    date_start    timestamp(6),
    discipline_id bigint not null,
    employee_id   bigint not null,
    group_id      bigint not null,
    id            bigint not null,
    room          varchar(10),
    primary key (id)
);

create table employee_discipline
(
    discipline_id bigint not null,
    employee_id   bigint not null
);

alter table if exists award add constraint award_employee_fk foreign key (employee_id) references employee;

alter table if exists department add constraint department_institute_fk foreign key (institute_id) references institute;

alter table if exists discipline add constraint discipline_department_fk foreign key (department_id) references department;

alter table if exists education_document add constraint education_document_employee_fk foreign key (employee_id) references employee;

alter table if exists employee add constraint employee_degree_fk foreign key (degree_id) references degree;

alter table if exists employee add constraint employee_employee_type_fk foreign key (employee_type_id) references employee_type;

alter table if exists employee_department add constraint employee_department_department_fk foreign key (department_id) references department;

alter table if exists employee_department add constraint employee_department_employee_fk foreign key (employee_id) references employee;

alter table if exists employee_job add constraint employee_job_employee foreign key (employee_id) references employee;

alter table if exists lesson add constraint lesson_discipline_fk foreign key (discipline_id) references discipline;

alter table if exists lesson add constraint lesson_learning_group foreign key (group_id) references learning_group;

alter table if exists lesson add constraint lesson_employee_fk foreign key (employee_id) references employee;

alter table if exists employee_discipline add constraint employee_discipline_discipline_fk foreign key (discipline_id) references discipline;

alter table if exists employee_discipline add constraint employee_discipline_employee_fk foreign key (employee_id) references employee;


commit ;