function FiSoroController(t,e,r){"use strict";function i(t){b=0,y=new Object,n(t);for(var e in t.filterGroups){var r=t.filterGroups[e];r.filterObjects(t)}t.numberSelectedOptions=b,p(t),t.isShowSelectedObjects=!1,s(t)}function n(t){t.selections=[];for(var e in t.Vectors){var r=t.Vectors[e];r[d]=0,t.selections[e]=r}}function s(t){for(var e in t.filterGroups){var r=t.filterGroups[e];r.updateOldGroupFilterValues(t)}}function a(t){for(var e in t.filterGroups){var r=t.filterGroups[e];for(var i in r.filters){var n=r.filters[i],s=n.id,a=n.value;if(a!=y[s])return!0}}return!1}function o(t){function e(t){for(var e in this.filters){var r=this.filters[e];y[r.id]=r.value}}function r(t){var e="";for(var r in this.filters){var i=this.filters[r];"true"==i.value?(e+="1",b++):e+="0",y[i.id]=i.value}if(0!=e){var n=parseInt(e,2),s=[];for(var r in t.selections){var a=t.selections[r],o=a[this.columnInData],l=parseInt(o,2),f=n&l;if(0!=f){for(var u=f.toString(2),c=0,h=0;h<u.length;h++){var v=u.charAt(h);"1"==v&&c++}a[d]+=c,s[s.length]=a}}t.selections=s}}this.filters=[],this.name=t.name,this.type=t.type,this.columnInData=parseInt(t.columnInData);for(var i in t.filters){var n=new u(this,t.filters[i].name,"Bit","false");this.filters[i]=n}this.filterObjects=r,this.updateOldGroupFilterValues=e}function l(t){function e(t){for(var e in this.filters){var r=this.filters[e];y[r.id]=r.value}}function r(t){var e=[];for(var r in t.selections){var i=t.selections[r],n=!0;for(var s in this.filters){var a=this.filters[s],o=i[a.columnInData];if(!a.match(o)){n=!1;break}}1==n&&(i[d]++,e[e.length]=i)}t.selections=e}this.filters=[],this.name=t.name,this.type=t.type;for(var i in t.filters){var n=t.filters[i],s=new h(this,n.name,"Text",n.value);s.columnInData=n.columnInData,this.filters[i]=s}O+=this.filters.length,this.filterObjects=r,this.updateOldGroupFilterValues=e}function f(t){function e(t){var e=[];for(var r in t.selections){var i=t.selections[r],n=!0;for(var s in this.filters){var a=this.filters[s],o=parseInt(i[a.columnInData]);if(!a.match(o)){n=!1;break}}1==n&&(i[d]++,e[e.length]=i)}t.selections=e}function r(t){for(var e in this.filters){var r=this.filters[e];y[r.id]=r.value}}this.filters=[],this.name=t.name,this.type=t.type;for(var i in t.filters){var n=t.filters[i],s=new c(this,n.name,n.type,parseInt(n.value));s.columnInData=n.columnInData,this.filters[i]=s}this.filterObjects=e,this.updateOldGroupFilterValues=r}function u(t,e,r,i){this.filterGroup=t,this.name=e,this.type=r,this.value=i,this.id="FiSoro_"+t.name+"_"+t.filters.length}function c(t,e,r,i){function n(t){return"minValue"==this.type?t>=this.value?!0:!1:"maxValue"==this.type?t<=this.value?!0:!1:void 0}u.call(this,t,e,r,i);this.match=n}function h(t,e,r,i){function n(t){if(0==this.value.length)return!0;var e=new RegExp(this.value);return e.test(t)}u.call(this,t,e,r,i);this.match=n}function v(t,e){return void 0===t?"Error by processing of file "+e+". False JSON format":"Error by load of file "+e+". HTTP Status: "+t}function p(t){t.selections=t.selections.sort(m)}function m(t,e){var r=e[d]-t[d];return r}var d="NumberMatches",y=new Object,b=0,O=0,w="data/data.json";null!=t.pathData&&(w=t.pathData),r.get(w).success(function(t){e.Vectors=t,n(e)}).error(function(t,e,r,i){alert(v(e,i.url))});var G="data/model.json";null!=t.pathModel&&(G=t.pathModel),r.get(G).success(function(t){e.filterGroups=[];for(var r in t.filterGroups){var i,n=t.filterGroups[r];"Bit Vector"==n.type?i=new o(n):"Text"==n.type?i=new l(n):"Number"==n.type?i=new f(n):alert("Error in model: unknown filter group type: "+n.type+" Model file: "+G),e.filterGroups[i.name]=i}e.isShowSelectedObjects=!1}).error(function(t,e,r,i){alert(v(e,i.url))}),e.$watch(function(){a(e)&&i(e)}),e.ShowSelections=function(){e.isShowSelectedObjects=!0},c.prototype=Object.create(u.prototype),c.prototype.constructor=u,h.prototype=Object.create(u.prototype),h.prototype.constructor=u}