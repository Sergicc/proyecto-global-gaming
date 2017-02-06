'use strict';

describe('Controller Tests', function() {

    describe('Sala Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSala, MockJuego, MockMensaje, MockIdioma;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSala = jasmine.createSpy('MockSala');
            MockJuego = jasmine.createSpy('MockJuego');
            MockMensaje = jasmine.createSpy('MockMensaje');
            MockIdioma = jasmine.createSpy('MockIdioma');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Sala': MockSala,
                'Juego': MockJuego,
                'Mensaje': MockMensaje,
                'Idioma': MockIdioma
            };
            createController = function() {
                $injector.get('$controller')("SalaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'proyectoGlobalGamingApp:salaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
