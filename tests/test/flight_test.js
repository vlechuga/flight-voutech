module.exports = function () {

    let reservations = [];

    it('/v1/flights/{flightCode}/seats/{numberOfSeats}: create a flight and return a 201 response', function (done) {
        api.post('/v1/flights/AB-1/seats/2')
            .set('Accept', 'application/json')
            .expect(201, done);
    });

    it('/v1/flights/{flightCode}/seats/available: get available seats on a flight should return a 200 response', function (done) {
        api.get('/v1/flights/AB-1/seats/available')
            .set('Accept', 'application/json')
            .expect(200)
            .end(function (err, res) {
                if (err)
                    return done(err);
                assert(res.body.seats.length > 0);
                done();
            });
    });

    it('/{flightCode}/reservations/reserve/{seatCode}/: Create a reservation return a 204 response', function (done) {
        api.post('/v1/flights/AB-1/reservations/reserve/A1')
            .set('Accept', 'application/json')
            .expect(204, done);
    });


    it('/{flightCode}/reservations/reserve/{seatCode}/: Create a reservation return a 204 response', function (done) {
        api.post('/v1/flights/AB-1/reservations/reserve/A1')
            .set('Accept', 'application/json')
            .expect(204, done);
    });

    it('/{flightCode}/reservations/reserve/{seatCode}/: Create a reservation return a 204 response', function (done) {
        api.post('/v1/flights/AB-1/reservations/reserve/A2')
            .set('Accept', 'application/json')
            .expect(204, done);
    });

    it('/v1/flights/{flightCode}/reservations/confirmed: get confirmed seats on a flight not valid should return a 404 response', function (done) {
        api.get('/v1/flights/AB-2/reservations/confirmed')
            .set('Accept', 'application/json')
            .expect(404)
        console.log('waiting 4 seconds');
        setTimeout(function () {
            done();
        }, 5000)
    });

    it('/v1/flights/{flightCode}/reservations/confirmed: get confirmed seats on a flight should return a 200 response', function (done) {
        api.get('/v1/flights/AB-1/reservations/confirmed')
            .set('Accept', 'application/json')
            .expect(200)
            .end(function (err, res) {
                if (err)
                    return done(err);
                assert(res.body.reservations.length == 2);
                done();
            });
    });

    it('/reservations/{reservationId}/release: Release a reservation return a 204 response', function (done) {
        api.post('/v1/flights/AB-1/reservations/release')
            .set('Accept', 'application/json')
            .expect(204, done);
    });

    it('/reservations/{reservationId}/release: Release a reservation return a 404 response', function (done) {
        api.post('/v1/flights/reservations/1000000/release')
            .set('Accept', 'application/json')
            .expect(404, done);
    });
};
