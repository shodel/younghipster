(function() {
    'use strict';
    angular
        .module('younghipsterApp')
        .factory('Mitarbeiter', Mitarbeiter);

    Mitarbeiter.$inject = ['$resource'];

    function Mitarbeiter ($resource) {
        var resourceUrl =  'api/mitarbeiters/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
