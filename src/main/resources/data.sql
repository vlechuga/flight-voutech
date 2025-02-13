
INSERT INTO users (user_id, user_name, user_email, user_password)
VALUES (100000, 'Vitor', 'vlechugamsc@gmail.com', '$2a$10$5VZRLGkLmvYjBPEPVokzfeB7RJo1CisyleOdB8AF6I3FzKSj8YJBu');


INSERT INTO flight (flight_id, flight_code)
VALUES
    (100000, 'ABC-01'),
    (100001, 'ABC-02');

INSERT INTO seat (seat_id, flight_id, seat_code, available, version)
VALUES
    (100000, 100000, 'A1', false, 0),
    (100001, 100000, 'A2', false, 0),
    (100002, 100000, 'A3', false, 0),
    (100003, 100000, 'A4', false, 0),
    (100004, 100000, 'A5', false, 0),

    (100005, 100001, 'A1', false, 0),
    (100006, 100001, 'A2', false, 0),
    (100007, 100001, 'A3', false, 0),
    (100008, 100001, 'A4', false, 0),
    (100009, 100001, 'A5', false, 0),
    (100010, 100001, 'A6', false, 0),
    (100011, 100001, 'A7', false, 0),
    (100012, 100001, 'A8', false, 0),
    (100013, 100001, 'A9', false, 0),
    (100014, 100001, 'A10', false, 0);