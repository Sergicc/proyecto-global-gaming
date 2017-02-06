'use strict';

describe('Controller Tests', function() {

    describe('Idioma Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockIdioma, MockJuego, MockSala;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockIdioma = jasmine.createSpy('MockIdioma');
            MockJuego = jasmine.createSpy('MockJuego');
            MockSala = jasmine.createSpy('MockSala');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Idioma': MockIdioma,
                'Juego': MockJuego,
                'Sala': MockSala
            };
            createController = function() {
                $injector.get('$controller')("IdiomaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'proyectoGlobalGamingApp:idiomaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
