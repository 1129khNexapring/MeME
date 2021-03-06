<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>퀴즈 등록 페이지</title>
<style>
	#quizWriteForm {
/* 		border: 1px solid black; */
		width:700px;
		text-align: left;
		margin: auto;
		border: 1px solid #ccc;
	 	border-collapse: separate;
		border-radius : 5px;
		background-color : white;
		font-size: 14px;
	}
	
	#type {
		--border: 1px solid #f26522;
		width: 100px;
		margin : 20px 0 15px 20px;
		font-size: 14px;
		border: none;
		--border-radius : 5px;
	}
	
		#type option {
			font-size: 14px;
		}
		
	/*문제 작성란*/
	#quizQuest {
		width:95%;
		resize: none;
		padding: 10px;
		margin: 15px;
		border: none;
	}
	/*정답 작성란*/
	#quizAnswer {
		width:95%;
		margin : 15px;
		margin-top: 0;
		margin-bottom: 0;
		padding: 10px;
		border: none;
	}
	/*객관식 보기 작성란*/
	#choice {
		width:100%;
		margin : 15px;
		margin-top: 0;
		margin-bottom: 0;
		padding: 10px;
		padding-top:0;
		--border: none;
	}
		#plus, #minus {
			width: 20px;
			height: 20px;
			text-align: center;
			background-color: white;
			border: 1px solid #ccc;
			border-radius: 50%;
		}
		
		#choice input[type="text"] {
			font-size: 14px;
			border: none;
			margin-bottom :5px;
		}
	
	#image_preview {
		width:95%;
		margin : 15px;
		margin-left: 20px;
		margin-top: 0;
		padding: 5px;
	}
		#image_preview div {
			font-size: 14px;
		}
	
	#att_zone{
		width: 98%;
		min-height:150px;
		padding:10px;
		border:1px dotted #f26522;
	}
	
	#att_zone:empty:before{
		content : attr(data-placeholder);
		color : #999;
		font-size:.9em;
	}
	
	#btnAtt {
		--border-radius : 5px;
		padding : 5px 0;
		margin-bottom: 10px;
		font-size:14px;
	}
	
	
	#title_name {
   			font-weight: bold;
	}
	
	/* 등록 버튼 */
 	#writeSubmitButton {
		border : 1px solid #f26522;
		background-color: #fef3ee;
		color: #f26522;
		border-radius : 5px;
		padding : 5px 10px;
		font-size:13px;
		font-weight: bold;
		margin-right: 5px;
 	}
 	
 	 #writeCancelButton {
		border : 1px solid #888;
		background-color: #ffffff;
		color: #888;
		border-radius : 5px;
		padding : 5px 10px;
		font-size:13px;
		font-weight: bold;
 	}
</style>
<script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	$(document).ready(function(){
		var type = document.querySelector("#type");
	    var plus = document.querySelector("#plus");
	    var minus = document.querySelector("#minus");
	    var chNum = 3;
	    var quizCh3 = document.querySelector("#quizCh3");
	    var quizCh4 = document.querySelector("#quizCh4");
		
	    type.addEventListener("change", function() {
	        var multiple = $("#type option:selected").val();
	        var choice = document.querySelector("#choice");
	        if(multiple == "M") {
	            choice.style.display = "block";
	        } else {
	        	choice.style.display = "none";
	        }
	    });
	
	    plus.addEventListener("click", function() {
	        if(chNum==3) {
	        	quizCh3.style.display = "block";
	            chNum++;
	        } else if(chNum==4) {
	        	quizCh4.style.display = "block";
	            chNum++;
	        }
	    });
	    
	    minus.addEventListener("click", function() {
	    	if(chNum==4) {
	    		quizCh3.style.display = "none";
	    		chNum--;
	    	} else if(chNum==5) {
	    		quizCh4.style.display = "none";
	    		chNum--;
	    	}
	    		
	    });
	    
	    
	    ( /* att_zone : 이미지들이 들어갈 위치 id, btn : file tag id
	    		https://jobtc.tistory.com/43 참고
	    		*/
	    		  imageView = function imageView(att_zone, btn){

	    		    var attZone = document.getElementById(att_zone);
	    		    var btnAtt = document.getElementById(btn)
	    		    var sel_files = []; // 첨부파일 목록
	    		    
	    		    // 이미지와 체크 박스를 감싸고 있는 div 속성
	    		    var div_style = 'display:inline-block;position:relative;'
	    		                  + 'width:150px;height:120px;margin:5px;border:1px solid #00f;z-index:1';
	    		    // 미리보기 이미지 속성
	    		    var img_style = 'width:100%;height:100%;z-index:none';
	    		    // 이미지안에 표시되는 체크박스의 속성
	    		    var chk_style = 'width:30px;height:30px;position:absolute;font-size:24px;'
	    		                  + 'right:0px;bottom:0px;z-index:999;background-color:rgba(255,255,255,0.1);color:#f00';
	    		  
	    		    // 파일을 선택했을때
	    		    btnAtt.onchange = function(e){
	    		      var files = e.target.files;
	    		      var fileArr = Array.prototype.slice.call(files)
	    		      for(f of fileArr){
	    		        imageLoader(f);
	    		      }
	    		    }  
	    		    
	    		  
	    		    // 탐색기에서 드래그앤 드롭 사용
	    		    attZone.addEventListener('dragenter', function(e){
	    		      e.preventDefault();
	    		      e.stopPropagation();
	    		    }, false)
	    		    
	    		    attZone.addEventListener('dragover', function(e){
	    		      e.preventDefault();
	    		      e.stopPropagation();
	    		      
	    		    }, false)
	    		  
	    		    attZone.addEventListener('drop', function(e){
	    		      var files = {};
	    		      e.preventDefault();
	    		      e.stopPropagation();
	    		      var dt = e.dataTransfer;
	    		      files = dt.files;
	    		      for(f of files){
	    		        imageLoader(f);
	    		      }
	    		      
	    		    }, false)
	    		    

	    		    
	    		    /*첨부된 이미지들을 배열에 넣고 미리보기 */
	    		    imageLoader = function(file){
	    		      sel_files.push(file);
	    		      var reader = new FileReader();
	    		      reader.onload = function(ee){
	    		        let img = document.createElement('img')
	    		        img.setAttribute('style', img_style)
	    		        img.src = ee.target.result;
	    		        attZone.appendChild(makeDiv(img, file));
	    		      }
	    		      
	    		      reader.readAsDataURL(file);
	    		    }
	    		    
	    		    /*첨부된 파일이 있는 경우 checkbox와 함께 attZone에 추가할 div를 만들어 반환 */
	    		    makeDiv = function(img, file){
	    		      var div = document.createElement('div')
	    		      div.setAttribute('style', div_style)
	    		      
	    		      var btn = document.createElement('input')
	    		      btn.setAttribute('type', 'button')
	    		      btn.setAttribute('value', 'x')
	    		      btn.setAttribute('delFile', file.name);
	    		      btn.setAttribute('style', chk_style);
	    		      btn.onclick = function(ev){
	    		        var ele = ev.srcElement;
	    		        var delFile = ele.getAttribute('delFile');
	    		        for(var i=0 ;i<sel_files.length; i++){
	    		          if(delFile== sel_files[i].name){
	    		            sel_files.splice(i, 1);      
	    		          }
	    		        }
	    		        
	    		        dt = new DataTransfer();
	    		        for(f in sel_files) {
	    		          var file = sel_files[f];
	    		          dt.items.add(file);
	    		        }
	    		        btnAtt.files = dt.files;
	    		        var p = ele.parentNode;
	    		        attZone.removeChild(p)
	    		      }
	    		      div.appendChild(img)
	    		      div.appendChild(btn)
	    		      return div
	    		    }
	    		  }
	    		)('att_zone', 'btnAtt')

	    
	    
	    
	});
</script>
</head>
<body>
	<form action="/quiz/write.me" method="post" id="quizWriteForm" enctype="multipart/form-data">
		
	    <select name="quizType" id="type">
	        <option value="" selected disabled>유형선택</option>
	        <option value="O">OX퀴즈</option>
	        <option value="M">객관식퀴즈</option>
	        <option value="S">단답형퀴즈</option>
	    </select>
	    
	    <hr style="width:670px; text-align:center;">
	    
	    <textarea id="quizQuest" name="quizQuest" cols="54" rows="10" placeholder="문제를 입력 해주세요"></textarea>
	    
	    <hr style="width:670px; text-align:center;">
	    
	    <input id="quizAnswer" type="text" name="quizAnswer" placeholder="정답을 입력 해주세요" size="51"><br>

	    <div id="choice" style="display: none;">
			<input style="border:none; margin:5px 0 5px 0; padding-left:0" type="text" name="quizCh1" placeholder="보기를 입력 해주세요" size="40"> &nbsp;&nbsp;
			<input type="button" id="plus" value="+">
			<input type="button" id="minus" value="-">
			<br>
			<input type="text" name="quizCh2" placeholder="보기를 입력 해주세요" size="40">
			<input type="text" name="quizCh3" id="quizCh3" placeholder="보기를 입력 해주세요" style="display: none" size="40">
			<input type="text" name="quizCh4" id="quizCh4" placeholder="보기를 입력 해주세요" style="display: none" size="40">
		</div>
		<hr style="width:670px; text-align:center;">
		<div id='image_preview'>
			<div>
				첨부 이미지 &nbsp&nbsp&nbsp&nbsp<input type='file' id='btnAtt' multiple='multiple' name="uploadFile" accept="image/*"/>
			</div> 
			
			<div style="margin-bottom: 10px;">이미지 미리보기</div>
			
			<div id='att_zone' 
			      data-placeholder='이미지 파일을 첨부 하려면 파일 선택 버튼을 클릭하거나 파일을 드래그앤드롭 하세요'>
			</div>
		</div>
		
		<hr style="width:670px; text-align:center; color: red;">
		<div align="right" style="padding:15px;">
			<input type="button" id="writeCancelButton" value="취소" onclick="history.back();">
		    <input type="submit" id="writeSubmitButton" value="등록하기">
		</div>
		
	   
    </form>
</body>
</html>