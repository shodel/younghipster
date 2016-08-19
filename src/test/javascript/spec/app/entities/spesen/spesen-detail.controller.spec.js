'use strict';

describe('Controller Tests', function() {

    describe('Spesen Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSpesen, MockMitarbeiter, MockProjekt;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSpesen = jasmine.createSpy('MockSpesen');
            MockMitarbeiter = jasmine.createSpy('MockMitarbeiter');
            MockProjekt = jasmine.createSpy('MockProjekt');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Spesen': MockSpesen,
                'Mitarbeiter': MockMitarbeiter,
                'Projekt': MockProjekt
            };
            createController = function() {
                $injector.get('$controller')("SpesenDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'younghipsterApp:spesenUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
