'use strict';

describe('Controller Tests', function() {

    describe('ValoracionJuego Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockValoracionJuego, MockUser, MockJuego;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockValoracionJuego = jasmine.createSpy('MockValoracionJuego');
            MockUser = jasmine.createSpy('MockUser');
            MockJuego = jasmine.createSpy('MockJuego');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ValoracionJuego': MockValoracionJuego,
                'User': MockUser,
                'Juego': MockJuego
            };
            createController = function() {
                $injector.get('$controller')("ValoracionJuegoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'proyectoGlobalGamingApp:valoracionJuegoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
