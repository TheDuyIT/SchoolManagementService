-- Users
INSERT INTO public.user_info (id, email, firstname, lastname, password, role, created_at, created_by, updated_at, updated_by, version) VALUES (1, 'admin@gmail.com', 'admin', 'nguyen', '$2a$10$OOA7whKe7AMFBVTeQZjEjeHuSc0jJ11LjTfPmWnYw9K8OKirmLkne', 'TEACHER', now(), 'SYSTEM', now(), 'SYSTEM', 0);
INSERT INTO public.user_info (id, email, firstname, lastname, password, role, created_at, created_by, updated_at, updated_by, version) VALUES (2, 'teacher1@gmail.com', 'teacher1', 'nguyen', '$2a$10$OOA7whKe7AMFBVTeQZjEjeHuSc0jJ11LjTfPmWnYw9K8OKirmLkne', 'TEACHER', now(), 'SYSTEM', now(), 'SYSTEM', 0);
INSERT INTO public.user_info (id, email, firstname, lastname, password, role, created_at, created_by, updated_at, updated_by, version) VALUES (3, 'teacher2@gmail.com', 'teache2', 'nguyen', '$2a$10$OOA7whKe7AMFBVTeQZjEjeHuSc0jJ11LjTfPmWnYw9K8OKirmLkne', 'TEACHER', now(), 'SYSTEM', now(), 'SYSTEM', 0);
INSERT INTO public.user_info (id, email, firstname, lastname, password, role, created_at, created_by, updated_at, updated_by, version) VALUES (4, 'teacher3@gmail.com', 'teacher3', 'nguyen', '$2a$10$OOA7whKe7AMFBVTeQZjEjeHuSc0jJ11LjTfPmWnYw9K8OKirmLkne', 'TEACHER', now(), 'SYSTEM', now(), 'SYSTEM', 0);
INSERT INTO public.user_info (id, email, firstname, lastname, password, role, created_at, created_by, updated_at, updated_by, version) VALUES (5, 'student1@gmail.com', 'student1', 'huynh', '$2a$10$OOA7whKe7AMFBVTeQZjEjeHuSc0jJ11LjTfPmWnYw9K8OKirmLkne', 'STUDENT', now(), 'SYSTEM', now(), 'SYSTEM', 0);
INSERT INTO public.user_info (id, email, firstname, lastname, password, role, created_at, created_by, updated_at, updated_by, version) VALUES (6, 'student2@gmail.com', 'student2', 'huynh', '$2a$10$OOA7whKe7AMFBVTeQZjEjeHuSc0jJ11LjTfPmWnYw9K8OKirmLkne', 'STUDENT', now(), 'SYSTEM', now(), 'SYSTEM', 0);
INSERT INTO public.user_info (id, email, firstname, lastname, password, role, created_at, created_by, updated_at, updated_by, version) VALUES (7, 'student3@gmail.com', 'student3', 'huynh', '$2a$10$OOA7whKe7AMFBVTeQZjEjeHuSc0jJ11LjTfPmWnYw9K8OKirmLkne', 'STUDENT', now(), 'SYSTEM', now(), 'SYSTEM', 0);
INSERT INTO public.user_info (id, email, firstname, lastname, password, role, created_at, created_by, updated_at, updated_by, version) VALUES (8, 'student4@gmail.com', 'student4', 'huynh', '$2a$10$OOA7whKe7AMFBVTeQZjEjeHuSc0jJ11LjTfPmWnYw9K8OKirmLkne', 'STUDENT', now(), 'SYSTEM', now(), 'SYSTEM', 0);

-- Questions
-- Question 1
INSERT INTO public.question (id, clause, answer_a, answer_b, answer_c, answer_d, correct_answer, created_at, updated_at, created_by, updated_by, version) VALUES (1, 'What is the result of 5 + 3?', '7', '8', '9', '10', 1, now(), now(), 'SYSTEM', 'SYSTEM', 0);
-- Question 2
INSERT INTO public.question (id, clause, answer_a, answer_b, answer_c, answer_d, correct_answer, created_at, updated_at, created_by, updated_by, version) VALUES (2, 'What is the square root of 25?', '3', '4', '5', '6', 2, now(), now(), 'SYSTEM', 'SYSTEM', 0);
-- Question 3
INSERT INTO public.question (id, clause, answer_a, answer_b, answer_c, answer_d, correct_answer, created_at, updated_at, created_by, updated_by, version) VALUES (3, 'What is the value of pi (π) to two decimal places?', '3.12', '3.14', '3.16', '3.18', 1, now(), now(), 'SYSTEM', 'SYSTEM', 0);
-- Question 4
INSERT INTO public.question (id, clause, answer_a, answer_b, answer_c, answer_d, correct_answer, created_at, updated_at, created_by, updated_by, version) VALUES (4, 'What is the result of 8 multiplied by 7?', '48', '56', '64', '72', 1, now(), now(), 'SYSTEM', 'SYSTEM', 0);
-- Question 5
INSERT INTO public.question (id, clause, answer_a, answer_b, answer_c, answer_d, correct_answer, created_at, updated_at, created_by, updated_by, version) VALUES (5, 'What is the value of 10 squared?', '50', '100', '150', '200', 2, now(), now(), 'SYSTEM', 'SYSTEM', 0);
-- Question 6
INSERT INTO public.question (id, clause, answer_a, answer_b, answer_c, answer_d, correct_answer, created_at, updated_at, created_by, updated_by, version) VALUES (6, 'What is the result of 12 divided by 3?', '2', '3', '4', '6', 1, now(), now(), 'SYSTEM', 'SYSTEM', 0);
-- Question 7
INSERT INTO public.question (id, clause, answer_a, answer_b, answer_c, answer_d, correct_answer, created_at, updated_at, created_by, updated_by, version) VALUES (7, 'What is the value of 5 factorial?', '20', '60', '120', '240', 2, now(), now(), 'SYSTEM', 'SYSTEM', 0);
-- Question 8
INSERT INTO public.question (id, clause, answer_a, answer_b, answer_c, answer_d, correct_answer, created_at, updated_at, created_by, updated_by, version) VALUES (8, 'What is the result of 3 to the power of 4?', '81', '64', '27', '16', 0, now(), now(), 'SYSTEM', 'SYSTEM', 0);
-- Question 9
INSERT INTO public.question (id, clause, answer_a, answer_b, answer_c, answer_d, correct_answer, created_at, updated_at, created_by, updated_by, version) VALUES (9, 'What is the area of a circle with a radius of 10 units (rounded to two decimal places)?', '31.41', '62.83', '94.25', '125.66', 1, now(), now(), 'SYSTEM', 'SYSTEM', 0);
-- Question 10
INSERT INTO public.question (id, clause, answer_a, answer_b, answer_c, answer_d, correct_answer, created_at, updated_at, created_by, updated_by, version) VALUES (10, 'What is the value of 2 to the power of 10?', '1024', '2048', '4096', '8192', 0, now(), now(), 'SYSTEM', 'SYSTEM', 0);
-- Question 11
INSERT INTO public.question (id, clause, answer_a, answer_b, answer_c, answer_d, correct_answer, created_at, updated_at, created_by, updated_by, version) VALUES (11, 'What is the result of 18 divided by 6?', '2', '3', '4', '6', 1, now(), now(), 'SYSTEM', 'SYSTEM', 0);
-- Question 12
INSERT INTO public.question (id, clause, answer_a, answer_b, answer_c, answer_d, correct_answer, created_at, updated_at, created_by, updated_by, version) VALUES (12, 'What is the value of the Euler''s number (e) rounded to two decimal places?', '2.57', '2.71', '2.86', '3.00', 1, now(), now(), 'SYSTEM', 'SYSTEM', 0);
-- Question 13
INSERT INTO public.question (id, clause, answer_a, answer_b, answer_c, answer_d, correct_answer, created_at, updated_at, created_by, updated_by, version) VALUES (13, 'What is the value of log base 10 of 100?', '1', '2', '3', '4', 2, now(), now(), 'SYSTEM', 'SYSTEM', 0);
-- Question 14
INSERT INTO public.question (id, clause, answer_a, answer_b, answer_c, answer_d, correct_answer, created_at, updated_at, created_by, updated_by, version) VALUES (14, 'What is the result of 25 minus 13?', '8', '10', '12', '14', 0, now(), now(), 'SYSTEM', 'SYSTEM', 0);
-- Question 15
INSERT INTO public.question (id, clause, answer_a, answer_b, answer_c, answer_d, correct_answer, created_at, updated_at, created_by, updated_by, version) VALUES (15, 'What is the value of the sine of 30 degrees (rounded to two decimal places)?', '0.50', '0.60', '0.70', '0.80', 0, now(), now(), 'SYSTEM', 'SYSTEM', 0);
-- Question 16
INSERT INTO public.question (id, clause, answer_a, answer_b, answer_c, answer_d, correct_answer, created_at, updated_at, created_by, updated_by, version) VALUES (16, 'What is the value of 12 factorial?', '479001600', '87178291200', '1307674368000', '20922789888000', 2, now(), now(), 'SYSTEM', 'SYSTEM', 0);
-- Question 17
INSERT INTO public.question (id, clause, answer_a, answer_b, answer_c, answer_d, correct_answer, created_at, updated_at, created_by, updated_by, version) VALUES (17, 'What is the result of 3 squared?', '6', '9', '12', '15', 1, now(), now(), 'SYSTEM', 'SYSTEM', 0);
-- Question 18
INSERT INTO public.question (id, clause, answer_a, answer_b, answer_c, answer_d, correct_answer, created_at, updated_at, created_by, updated_by, version) VALUES (18, 'What is the value of 4 to the power of 3?', '32', '64', '128', '256', 1, now(), now(), 'SYSTEM', 'SYSTEM', 0);
-- Question 19
INSERT INTO public.question (id, clause, answer_a, answer_b, answer_c, answer_d, correct_answer, created_at, updated_at, created_by, updated_by, version) VALUES (19, 'What is the result of 9 divided by 3?', '2', '3', '4', '6', 1, now(), now(), 'SYSTEM', 'SYSTEM', 0);
-- Question 20
INSERT INTO public.question (id, clause, answer_a, answer_b, answer_c, answer_d, correct_answer, created_at, updated_at, created_by, updated_by, version) VALUES (20, 'What is the value of the cosine of 60 degrees (rounded to two decimal places)?', '0.50', '0.60', '0.70', '0.80', 0, now(), now(), 'SYSTEM', 'SYSTEM', 0);

-- Examination
INSERT INTO public.examination (duration_in_minute, version, created_at, id, updated_at, created_by, title, updated_by) VALUES (20, 0, '2024-03-31 21:15:10.920460', 1, '2024-03-31 21:15:10.920460', 'teacher1@gmail.com', 'Examination name hihi', 'teacher1@gmail.com');


-- ExaminationQuestion
INSERT INTO public.examination_question (examination_id, question_id) VALUES (1, 4);
INSERT INTO public.examination_question (examination_id, question_id) VALUES (1, 10);
INSERT INTO public.examination_question (examination_id, question_id) VALUES (1, 3);
INSERT INTO public.examination_question (examination_id, question_id) VALUES (1, 1);


-- UserExamination
INSERT INTO public.user_examination (score, status, version, created_at, done_time, examination_id, id, start_doing_time, updated_at, user_id, created_by, updated_by) VALUES (0, 0, 0, '2024-03-31 23:21:23.138221', null, 1, 1, null, '2024-03-31 23:21:23.138221', 5, 'teacher1@gmail.com', 'teacher1@gmail.com');
INSERT INTO public.user_examination (score, status, version, created_at, done_time, examination_id, id, start_doing_time, updated_at, user_id, created_by, updated_by) VALUES (0, 0, 0, '2024-03-31 23:21:23.144672', null, 1, 2, null, '2024-03-31 23:21:23.144672', 7, 'teacher1@gmail.com', 'teacher1@gmail.com');
INSERT INTO public.user_examination (score, status, version, created_at, done_time, examination_id, id, start_doing_time, updated_at, user_id, created_by, updated_by) VALUES (0, 0, 0, '2024-03-31 23:21:23.144672', null, 1, 3, null, '2024-03-31 23:21:23.144672', 6, 'teacher1@gmail.com', 'teacher1@gmail.com');

