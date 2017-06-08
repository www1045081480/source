function getUrlVars()
{
	var vars = [], hash;
	var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	for(var i = 0; i < hashes.length; i++) {
		hash = hashes[i].split('=');
		vars.push(hash[0]);
		vars[hash[0]] = decodeURI(hash[1]);
	}
	return vars;
}

function number_format(num) {
	num = String(num);
	while(num != (num = num.replace(/^(-?\d+)(\d{3})/, "$1,$2")));
	return num;
}
function in_array(needle, arraylist) {
	if(needle instanceof Function) {
		for( var i=0, l=arraylist.length; i < l; i++ ) {
			if(needle(arraylist[i])) {
				return true;
			}
		}
	} else {
		for( var i=0, l=arraylist.length; i < l; i++ ) {
			if(arraylist[i] == needle) {
				return true;
			}
		}
	}
	return false;
}
function htmlspecialchars(ch){
	if(typeof ch != 'string') {
		return ch;
	}
	ch = ch.replace(/&/g,"&amp;") ;
    ch = ch.replace(/"/g,"&quot;") ;
    ch = ch.replace(/'/g,"&#039;") ;
    ch = ch.replace(/</g,"&lt;") ;
    ch = ch.replace(/>/g,"&gt;") ;
    return ch ;
}

if (!Array.indexOf) {
    Array.prototype.indexOf = function(o) {
        for (var i in this) {
            if (this[i] == o) {
                return i;
            }
        }
        return -1;
    }
}