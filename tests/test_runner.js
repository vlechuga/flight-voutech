
should = require('chai').should(),
expect = require('chai').expect ,
supertest = require('supertest'),
api = supertest('http://localhost:8080/api'),
assert = require('assert'),

(() => {

    describe("FLIGHT API", require('./test/flight_test'));

})();

