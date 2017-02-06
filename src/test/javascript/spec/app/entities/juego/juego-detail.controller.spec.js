'use strict';

describe('Controller Tests', function() {

    describe('Juego Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockJuego, MockSala, MockIdioma;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockJuego = jasmine.createSpy('MockJuego');
            MockSala = jasmine.createSpy('MockSala');
            MockIdioma = jasmine.createSpy('MockIdioma');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Juego': MockJuego,
                'Sala': MockSala,
                'Idioma': MockIdioma
            };
            createController = function() {
                $injector.get('$controller')("JuegoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'proyectoGlobalGamingApp:juegoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
