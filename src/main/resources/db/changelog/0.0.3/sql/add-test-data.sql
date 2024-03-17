insert into department (foundation_year, id, institute_id, room, name)
values (1974, nextval('department_seq'), 7, 'Б-204', 'Кафедра высшей математики'),
       (1956, nextval('department_seq'), 5, 'В-401', 'Кафедра физики'),
       (1989, nextval('department_seq'), 10, 'М-807', 'Кафедра истории'),
       (1970, nextval('department_seq'), 1, 'З-502', 'Кафедра ВМСС');

insert into discipline (id, name, department_id)
values (nextval('discipline_seq'), 'Математический анализ', 1),
       (nextval('discipline_seq'), 'Линейная алгебра', 1),
       (nextval('discipline_seq'), 'Физика', 2),
       (nextval('discipline_seq'), 'История', 3),
       (nextval('discipline_seq'), 'Основы программирования', 4);

insert into employee (contract_date, contract_expire_date, date_of_birth, emp_record_date_start, is_active,
                      is_emp_record_digital, passport_serie, passport_number, passport_dep_code, degree_id,
                      employee_type_id, id, emp_record_num, contract_num, rank, passport_giver_organ,
                      registration_address, fio)
values ('2011-04-12', '2023-12-25', '1978-12-20', '2000-01-15', true, true, '4513', '447417', '770-025', 4, 1, nextval('employee_seq'),
        '123A', 'К1', 'Доцент', 'МВД РФ', 'г. Москва, ул. 5-я Парковая, д. 10, кв. 154', 'Гасин Михаил Александрович'),
       ('2012-05-22', '2022-12-25', '1953-12-03', '1971-12-02', true, false, '2715', '123098', '634-123', 5, 1, nextval('employee_seq'),
        '324B', 'К2', 'Доцент', 'УФМС', 'г. Москва, ул. Авиамоторная, д. 53, кв. 12', 'Марчинская Софья Алексеевна'),
       ('2005-09-13', '2024-12-01', '1985-07-23', '2005-09-13', true, true, '5723', '123456', '783-239', 6, 1, nextval('employee_seq'),
        '923C', 'K3', 'Профессор', 'УФМС', 'г. Москва ул. Энергетическая д. 15 кв. 28', 'Рогалёв Николай Дмитриевич'),
       ('2018-12-20', '2025-12-20', '1999-12-20', '2018-12-20', true, false, '4912', '213865', '923-520', 4, 1, nextval('employee_seq'),
        '023D', 'K4', 'Ассистент', 'МВД РФ', 'г. Москва ул. Первомайская д. 12 кв. 25', 'Савельев Сергей Дмитриевич'),
       ('2019-11-09', '2023-12-29', '1970-09-09', '1990-12-02', true, false, '2373', '981239', '982-380', 1, 3, nextval('employee_seq'),
        '823E', 'K5', null, 'МВД РФ', 'г. Москва ул. Первомайская д. 8 кв. 12', 'Невский Александр Юрьевич');

insert into employee_job (date_end, date_start, salary, employee_id, id, position, organization_name)
values (null, '2011-04-12', 15000.00, 1, nextval('employee_job_seq'), 'Доцент', 'НИУ МЭИ'),
       (null, '2012-05-22', 19000.00, 2, nextval('employee_job_seq'), 'Доцент', 'НИУ МЭИ'),
       (null, '2005-09-13', 25000.00, 3, nextval('employee_job_seq'), 'Профессор', 'НИУ МЭИ'),
       (null, '2018-12-20', 11000.00, 4, nextval('employee_job_seq'), 'Ассистент', 'НИУ МЭИ'),
       (null, '2019-11-09', 14000.00, 5, nextval('employee_job_seq'), 'Дворник', 'НИУ МЭИ');

insert into lesson (date_end, date_start, discipline_id, employee_id, group_id, id, room)
values ('2023-12-20 09:20:00', '2023-12-20 10:55:00', 1, 1, 1, nextval('lesson_seq'), 'Б-200'),
       ('2023-12-20 11:10:00', '2023-12-20 12:45:00', 1, 1, 1, nextval('lesson_seq'), 'Б-200'),
       ('2023-12-20 13:45:00', '2023-12-20 15:20:00', 1, 1, 2, nextval('lesson_seq'), 'А-300'),
       ('2023-12-20 09:20:00', '2023-12-20 10:55:00', 2, 2, 2, nextval('lesson_seq'), 'Ж-410'),
       ('2023-12-20 11:10:00', '2023-12-20 12:45:00', 2, 2, 2, nextval('lesson_seq'), 'Ж-410'),
       ('2023-12-20 13:45:00', '2023-12-20 15:20:00', 2, 2, 1, nextval('lesson_seq'), 'Ж-412');

insert into employee_discipline (discipline_id, employee_id)
values (1, 1),
       (2, 2);

insert into employee_department (employee_id, department_id)
values (1, 1),
       (2, 1),
       (3, 2),
       (4, 3),
       (5, 4);

insert into award (date_recieve, employee_id, id, giver_organization, text)
values ('2013-12-04', 1, nextval('award_seq'), 'Всемирный научный форум', 'За особые заслуги'),
       ('2004-01-27', 2, nextval('award_seq'), 'Тьюринг МЭИ', 'За великолепное кураторство'),
       ('2005-01-15', 2, nextval('award_seq'), 'Тьюринг МЭИ', 'За великолепное кураторство повторно');

commit ;