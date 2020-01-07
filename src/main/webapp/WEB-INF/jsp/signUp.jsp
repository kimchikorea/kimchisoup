<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Jsp page sample</title>
</head>
<body>
<div class="signup-box z-depth-2">
     <h6 id="signUpFail" ></h6>
         <h4>Create a Account</h4>

         <form class="signup-form" action="/member" method="POST">
           <div class="row">
               <div class="input-field col s12">
                 <input id="name" name="uid" type="text" class="validate">
                 <label for="name">Username1</label>
               </div>
           </div>

           <div class="row">
               <div class="input-field col s12">
                   <input id="email" name="uemail" type="email" class="validate">
                   <label for="email">Email</label>
                 </div>
               </div>

          <div class="row">
               <div class="input-field col s12">
                 <input id="password" name="upw" type="password" class="validate">
                 <label for="password">Password</label>
               </div>
          </div>
             <input class="signup-btn waves-effect waves-light btn" type="submit" value="가입하기" />
         </form>

     </div>
</html>
