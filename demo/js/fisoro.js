/**
$rootScope - Container for information exchange with application and other controllers
$scope - Container for model (information about filter values)
$http - Angular service for asynchronously communication with server (in our case - load data from server).
**/
function FiSoroController($rootScope, $scope, $http) {
/**
 * @License FiSoro v.0.9.5
 * (c) 2015 Dr. Viktor Sirotin. http: //www.sirotin.eu
 * License type: MIT
 */
'use strict';
//------Constants------------
var NUMBER_MATCHES = 'NumberMatches';

//------Global variables-----
var oldFilterValues = new Object(); //Place for saving old values of filter to comparing after model change
var numberSelectedOptions = 0; //Number all selected options
var currentPositionInObjectVector = 0;


    //Load data about objects
    var pathData = 'data/data.json'; //Default path for file with model

    if($rootScope.pathData != null){
        pathData = $rootScope.pathData;
    }

    //Load data about objects from server
    $http.get(pathData).success(function(response) {
        //Contains information about pages to be selected/presented
        $scope.Vectors = response; //Save data in scope
        initSelections($scope); //Initial state
    }).error(function(data, status, headers, config){
            alert(getErrorMessage(status, config.url));
    }); // End  $http.get('data/data.json'

    //Load model data (about filters and sorters)
    var pathModel = 'data/model.json'; //Default value for file with model

    if($rootScope.pathModel != null){
        pathModel = $rootScope.pathModel;
    }
    $http.get(pathModel).success(function(response) {

         $scope.filterGroups = [];
         for(var i in  response.filterGroups){

            var filterData = response.filterGroups[i];
            var filterGroup;
            //Create filter group object depends from type loaded filters
            if (filterData.type == 'Bit Vector'){
                filterGroup = new FilterGroupBitVector(filterData);

            }else if (filterData.type == 'Text'){
               filterGroup = new FilterGroupText(filterData);
            }else if (filterData.type == 'Number'){
                  filterGroup = new FilterGroupNumber(filterData);
             }else {
                alert('Error in model: unknown filter group type: ' + filterData.type + " Model file: " + pathModel);
             }

            $scope.filterGroups[filterGroup.name] = filterGroup; //Save group in scope
         } //for i

         //Help variable. Is true if selected objects should be shown in list
         $scope.isShowSelectedObjects = false;

    }).error(function(data, status, headers, config){
            alert(getErrorMessage(status, config.url));
     }); //End  $http.get('data/gui.json')

    //Will be called if model have been changed
    $scope.$watch(function() {
        //Because watch can be called not only if some filter changed
        //We check if filter values ware changes since last call this function
        if(someFilterValueChanged($scope)){
            updateSelection($scope)
        }
    });

    //Will be called from GUI if user will see selected objects
    $scope.ShowSelections = function(){
        $scope.isShowSelectedObjects = true;
    } //End  $scope.ShowSelections
//} //End of FiSoroController


//Update list of selected objects after changing of some filter
function updateSelection($scope){
    //Variable initialization
    numberSelectedOptions = 0;
    oldFilterValues = new Object();

    initSelections($scope)

    for(var i in $scope.filterGroups){
        var filterGroup = $scope.filterGroups[i];
          filterGroup.filterObjects($scope);
    }

     $scope.numberSelectedOptions = numberSelectedOptions
     sortSelections($scope);
     $scope.isShowSelectedObjects = false;
     updateOldFilterValues($scope);
} //updateSelection

function initSelections($scope){
    $scope.selections = [];
    for(var i in $scope.Vectors){
        var selection = $scope.Vectors[i];
        selection[NUMBER_MATCHES] = 0;
        $scope.selections[i] = selection;
    }
}

function updateOldFilterValues($scope){
    for(var i in $scope.filterGroups){
        var filterGroup = $scope.filterGroups[i];
        filterGroup.updateOldGroupFilterValues($scope);
    }
}

//Returns true if filter value have been changed since last processing
function someFilterValueChanged($scope){
    for(var i in $scope.filterGroups){
        var filterGroup = $scope.filterGroups[i];
        for(var j in filterGroup.filters){
            var filter = filterGroup.filters[j];
            var id = filter.id;
            var value = filter.value;
            if( value != oldFilterValues[id]){
                return true;
            }
        } //j
    } //i
    return false;
} //someFilterValueChanged

//----------------- Model objects -------------
//Object represents bit vector filter group
function FilterGroupBitVector(filterData){
    this.filters = [];
    this.name = filterData.name;
    this.type = filterData.type;
    this.columnInData = parseInt(filterData.columnInData);
    for(var i in filterData.filters){
        var filter = new Filter(this, filterData.filters[i].name, 'Bit', 'false');
        this.filters[i] = filter;
    } // for i

    this.filterObjects = filterObjects;
    this.updateOldGroupFilterValues = updateOldGroupFilterValues;

    function updateOldGroupFilterValues($scope){
        for(var i in this.filters){
            var filter = this.filters[i];
            oldFilterValues[filter.id] = filter.value;
        }
    }

    function filterObjects($scope){
        var matchVector = '';
        for(var i in this.filters){
            var filter = this.filters[i];
            if(filter.value == 'true'){
               matchVector += '1';
               numberSelectedOptions++;
            }else{
               matchVector += '0';
            }
            oldFilterValues[filter.id] = filter.value;
        }
        if(matchVector == 0){
            return;
        }
        var a = parseInt(matchVector,2);
        var selectionsTmp = [];
        for(var i in $scope.selections){
            var selection = $scope.selections[i];
            var bitVector = selection[this.columnInData];

            var b = parseInt(bitVector,2);
            var res = a & b;
            //Variant OR
            if(res != 0){
                var resAsString = res.toString(2);
                //Calculate amount of 1 in string
                var numberMatches = 0;
                for(var j = 0; j < resAsString.length;j++){
                    var c = resAsString.charAt(j);
                    if(c == '1'){
                        numberMatches++;
                    }
                } //for j
                selection[NUMBER_MATCHES] += numberMatches;
                selectionsTmp[selectionsTmp.length] = selection;
            } //if res
        } //for i
        $scope.selections = selectionsTmp;
    }//this.filterObjects
} //FilterGroupBitVector

function FilterGroupText(filterData){
    this.filters = [];
    this.name = filterData.name;
    this.type = filterData.type;
    for(var i in filterData.filters){
       var f = filterData.filters[i];
       var filter = new FilterText(this, f.name, 'Text', f.value);
       filter.columnInData = f.columnInData;
       this.filters[i] = filter;
    } // for i

    currentPositionInObjectVector += this.filters.length;
    this.filterObjects = filterObjects;
    this.updateOldGroupFilterValues = updateOldGroupFilterValues;

    function updateOldGroupFilterValues($scope){
        for(var i in this.filters){
             var filter = this.filters[i];
             oldFilterValues[filter.id] = filter.value;
        }
    } //End of updateOldGroupFilterValues

    function filterObjects($scope){
        var selectionsTmp = [];
        for(var i in $scope.selections){
            var selection = $scope.selections[i];
            var selected = true;
            for(var j in this.filters){
                var filter = this.filters[j];
                var value = selection[filter.columnInData];
                if(!filter.match(value)){
                    selected = false;
                    break;
                }
            } //for j
            if(selected == true){
                selection[NUMBER_MATCHES]++;
                selectionsTmp[selectionsTmp.length] = selection;
            }
        }//for i
        $scope.selections = selectionsTmp;
    } //End of filterObjects
} //FilterGroupText

function FilterGroupNumber(filterData){
    this.filters = [];
    this.name = filterData.name;
    this.type = filterData.type;
    for(var i in filterData.filters){
        var f = filterData.filters[i];
        var filter = new FilterNumber(this, f.name, f.type, parseInt(f.value));
        filter.columnInData = f.columnInData;
        this.filters[i] = filter;
    } // for i


    this.filterObjects = filterObjects;

    function filterObjects($scope){
        var selectionsTmp = [];
        for(var i in $scope.selections){
            var selection = $scope.selections[i];
            var selected = true;
            for(var j in this.filters){
                var filter = this.filters[j];
                var value = parseInt(selection[filter.columnInData]);
                if(!filter.match(value)){
                    selected = false;
                    break;
                }
            } //for j
            if(selected == true){
                selection[NUMBER_MATCHES]++;
                selectionsTmp[selectionsTmp.length] = selection;
            }
        }//for i
        $scope.selections = selectionsTmp;
    } //End of filterObjects

    this.updateOldGroupFilterValues = updateOldGroupFilterValues;

    function updateOldGroupFilterValues($scope){
        for(var i in this.filters){
             var filter = this.filters[i];
             oldFilterValues[filter.id] = filter.value;
        }
    } //updateOldGroupFilterValues
} //FilterGroupNumber

//Simply filter object
function Filter(filterGroup, name, type, value){
    this.filterGroup = filterGroup;
    this.name = name;
    this.type = type;
    this.value = value;
    this.id = 'FiSoro_' + filterGroup.name + '_' + filterGroup.filters.length;
}

//Filter object for number
function FilterNumber(filterGroup, name, type, value){
    Filter.call(this, filterGroup, name, type, value);
    var columnInData;
    this.match = match;

    function match(value){
        if(this.type == 'minValue'){
            return (value >= this.value)? true : false;
        }

        if(this.type == 'maxValue'){
            return (value <= this.value)? true : false;
        }
    }
}
FilterNumber.prototype = Object.create(Filter.prototype);
FilterNumber.prototype.constructor = Filter;

//Filter object for text
function FilterText(filterGroup, name, type, value){
    Filter.call(this, filterGroup, name, type, value);
    var columnInData;
    this.match = match;

    function match(value){
        if(this.value.length == 0) return true;

        var re = new RegExp(this.value);
        return re.test(value);
    }
}
FilterText.prototype = Object.create(Filter.prototype);
FilterText.prototype.constructor = Filter;

//------------Help functions for error processing------------------
function getErrorMessage(status, url){
    if(status === undefined){
        return "Error by processing of file " + url + ". False JSON format";
    }
    return "Error by load of file " + url + ". HTTP Status: " + status;
}
//------------Help functions for sort object list------------------
function sortSelections($scope){
    $scope.selections = $scope.selections.sort(compareVectors);
}

function compareVectors(a, b){
    var dif =  b[NUMBER_MATCHES] - a[NUMBER_MATCHES];
    return dif;
}
} //End of FiSoroController