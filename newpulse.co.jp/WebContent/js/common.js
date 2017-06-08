/*************************************************
 *
 * 共通JavaScript
 *
 *************************************************/

 /********************************************************************
  *  外部変数
  *******************************************************************/
    var submitFlg = 0;			// 二度押し禁止用フラグ

 /********************************************************************
  *  function : 共通サブミット処理
  *  引数：		なし
  *******************************************************************/
	function checkDoubleClick () {
	  if (submitFlg == 0) {
	    submitFlg = 1;
		return true;
	  } else {
		return false;
	  }
	}

 /********************************************************************
  *  function : 削除確認
  *  引数：		なし
  *******************************************************************/
	function confirmDeletion () {
		return confirm('削除しますか？');
	}

 /********************************************************************
  *  function : 確認メッセージ
  *  引数：		なし
  *******************************************************************/
	function confirmMessage (conf_message) {
		return confirm(conf_message);
	}

 /********************************************************************
  *  function : テキストフィールド削除
  *  引数：		なし
  *******************************************************************/
	function clearText (field) {
		if(field.defaultValue == field.value) {
			field.value = "";
		}
	}

 /********************************************************************
  *  function : 新規ウィンドウ
  *  引数：		なし
  *******************************************************************/
	function openWindow(url, window_name){ 

		window.open(url, window_name, "width=550,height=320,scrollbars=yes,resizable=yes,status=yes"); 

	}

 /********************************************************************
  *  function : サブミット処理
  *  引数：		なし
  *******************************************************************/
	function submitForm(action){ 

		var frms = document.forms[0];
		frms.action = action;
		frms.target = "_blank";
		frms.submit();
		frms.target = "";
		frms.action = 'item_template_edit.php';
	}

 /********************************************************************
  *  function : 改ページ用
  *  引数：	action						遷移先のプログラム名
  *			list_count					一覧表示件数
  *			page						ページ番号
  *******************************************************************/
	function submitSelectPage(action, list_count, page) {

		// csvデータ送信用フォーム
		var csvfrms = document.forms["csv"];
		csvfrms.action = action;

		for(i = 1; i <= list_count; i++) {

			// 一覧各フォーム
			var frms 	= document.forms["list" + i];

			// ページ番号をcsvデータ用フォームにうつす
			csvfrms.elements["page"].value = page;

			if(frms.elements["csv_output"+"["+i+"]"].checked == true) {
				csvfrms.elements["csv_output_on"+"["+i+"]"].value = frms.elements["sid"].value;
			} else {
				csvfrms.elements["csv_output_off"+"["+i+"]"].value = frms.elements["sid"].value;
			}
		}

		// フォーム送信
		csvfrms.submit();
	}
 /********************************************************************
  *  function : エンターキー押下でフォーム送信停止処理
  *  引数：	evt		イベント
  *******************************************************************/
	function BlockEnter(evt){
		evt = (evt) ? evt : event; 
		var charCode=(evt.charCode) ? evt.charCode : 
			((evt.which) ? evt.which : evt.keyCode);
		if ( Number(charCode) == 13 || Number(charCode) == 3) {
			return false;
		} else {
			return true;
		}
	}

 /********************************************************************
  *  function : サブミット処理
  *  引数：		なし
  *******************************************************************/
	function submitFormBlockEnter(formid, action, buttonname){ 

		var frms = document.forms[formid];
		frms.action = action;
		frms.elements[buttonname].value = "1";
		document.forms[formid].elements[buttonname].onkeypress=BlockEnter;
		frms.submit();
	}
	
 /********************************************************************
  *  function : 全て選択する・全て選択を解除（メールアドレス検索）
  *  引数：		なし
  *******************************************************************/
	function checkCheckboxMailMagazine(formname,check,element){ 
	var formObject = document.forms[formname].elements[element];

	for(i=0;i<formObject.length;i++){
		formObject[i].checked = check;
	}

	}
/********************************************************************
*  function : エンターボタン押下制御
*  引数：		keycode
/*******************************************************************/
    function checkBlockEnter(){ 

        // キーコード取得
        keycode = event.keyCode;

        // エンターボタンは制御
    	if ( Number(keycode) == 13 || Number(keycode) == 3) {
            event.keyCode = 0; 
            event.returnValue = false; 
    	}
    }

/********************************************************************
*  function : 検索条件取得（日付）
*  引数：		
/*******************************************************************/
    function getSearchDate(form,lastyear){ 

        var date = new Date();
        var dateValue;

        var element = new Array(6);
        element[0] = "startYear";
        element[1] = "startMonth";
        element[2] = "startDay";
        element[3] = "endYear";
        element[4] = "endMonth";
        element[5] = "endDay";

        var YearValue  = date.getFullYear();
        var MonthValue = date.getMonth()+1;

        for(elementId=0;elementId<element.length;elementId++){

            var formObj = document.getElementById(element[elementId]);

            for(i=0;i<formObj.length;i++){

                if(elementId == 0 || elementId == 3){
                    dateValue = date.getFullYear();

                } else if (elementId == 1 || elementId == 4){
                    dateValue = date.getMonth()+1;

                } else {

                    if(lastyear == 1){
                        if(elementId == 5 ){
                            if( MonthValue == 4 || MonthValue == 6 || MonthValue == 9 || MonthValue == 11){
                                date.setMonth((MonthValue+1));
                                date.setDate(30);
                            } else if (MonthValue == 2){
                                if(((YearValue%4) == 0 && (YearValue%100) != 0)){
                                    date.setMonth(3);
                                    date.setDate(29);
                                } else {
                                    date.setMonth(3);
                                    date.setDate(28);
                                }
                            } else {
                                date.setDate(31);
                            }
                        } else {
                            dateValue = date.setDate(1);;
                        }
                    }



                    dateValue = date.getDate();

                }

                if( formObj.options[i].value == dateValue ){
                    formObj.options[i].selected = true;
                }

            }

        }
    }

/********************************************************************
*  function     :エンターボタン押下でタブ移動
*  引数         :なし
/*******************************************************************/
    function changeKeyCodeEntertoTab(){ 
        if (!(event.srcElement.type == "image" || event.srcElement.type == "textarea" || event.srcElement.type == "button" || event.srcElement.type == "submit")) {
            if (event.keyCode == 13) {
              event.keyCode = 9;
            }
        }
    }