(this.webpackJsonpfront=this.webpackJsonpfront||[]).push([[9],{141:function(n,e,t){"use strict";t.r(e);var a=t(37),r=t(0),o=t(50),c=t(49),i=t(40),s=t.n(i),b=t(2),d=t(12),l=t(44),u=t(43),j=t(51),p=t(6);e.default=function(){var n=Object(l.a)("http://3.38.35.210:8080/api/v1/user/me",u.a).data,e=Object(c.a)(""),t=Object(a.a)(e,2),i=t[0],x=t[1],h=Object(c.a)(""),g=Object(a.a)(h,2),f=g[0],O=g[1],m=Object(c.a)(""),v=Object(a.a)(m,3),w=v[0],k=v[2],y=Object(c.a)(""),C=Object(a.a)(y,3),S=C[0],_=C[2],z=Object(r.useState)(!1),I=Object(a.a)(z,2),P=I[0],J=I[1],L=Object(r.useState)(""),U=Object(a.a)(L,2),A=U[0],H=U[1],N=Object(r.useState)(!1),D=Object(a.a)(N,2),E=D[0],M=D[1],T=Object(r.useState)(!1),q=Object(a.a)(T,2),B=q[0],F=q[1],G=Object(r.useCallback)((function(n){k(n.target.value),J(n.target.value!==S)}),[S]),K=Object(r.useCallback)((function(n){_(n.target.value),J(n.target.value!==w)}),[w]),Q=Object(r.useCallback)((function(n){n.preventDefault(),F(!0),P||(console.log("\uc11c\ubc84\ub85c \ud68c\uc6d0\uac00\uc785 gogo"),H(""),M(!1),s.a.post("http://3.38.35.210:8080/api/v1/signup",{username:i,email:f,password:w}).then((function(n){console.log(n),M(!0),F(!1),alert("SignUp Success! Login Please")})).catch((function(n){console.log(n.response),H(n.response.data.errorMsg),F(!1)})).finally((function(){})))}),[i,f,w,P]);return E?Object(p.jsx)(b.a,{to:"/"}):n?Object(p.jsx)(b.a,{to:"/home"}):Object(p.jsxs)("div",{id:"container",children:[Object(p.jsx)(o.d,{children:"Join Pilas"}),Object(p.jsxs)(o.c,{onSubmit:Q,children:[Object(p.jsxs)(o.f,{id:"nickname-label",children:[Object(p.jsx)("span",{children:"Username"}),Object(p.jsx)("div",{children:Object(p.jsx)(o.e,{type:"text",id:"nickname",name:"nickname",value:i,onChange:x})})]}),!i&&Object(p.jsx)(o.b,{children:"\uc720\uc800\ub124\uc784\uc744 \uc785\ub825\ud574\uc8fc\uc138\uc694."}),A&&Object(p.jsx)(o.b,{children:A}),Object(p.jsxs)(o.f,{id:"email-label",children:[Object(p.jsx)("span",{children:"Email"}),Object(p.jsx)("div",{children:Object(p.jsx)(o.e,{type:"email",id:"email",name:"email",value:f,onChange:O})})]}),!f&&Object(p.jsx)(o.b,{children:"\uc774\uba54\uc77c\uc744 \uc785\ub825\ud574\uc8fc\uc138\uc694."}),Object(p.jsxs)(o.f,{id:"password-label",children:[Object(p.jsx)("span",{children:"Password"}),Object(p.jsx)("div",{children:Object(p.jsx)(o.e,{type:"password",id:"password",name:"password",value:w,onChange:G})})]}),Object(p.jsxs)(o.f,{id:"password-check-label",children:[Object(p.jsx)("span",{children:"Confirm your Password"}),Object(p.jsx)("div",{children:Object(p.jsx)(o.e,{type:"password",id:"password-check",name:"password-check",value:S,onChange:K})}),P&&Object(p.jsx)(o.b,{children:"\ube44\ubc00\ubc88\ud638\uac00 \uc77c\uce58\ud558\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4."})]}),Object(p.jsx)(o.a,{type:"submit",children:"Create account"})]}),Object(p.jsxs)(o.g,{children:["Already have an account?\xa0",Object(p.jsx)(d.b,{to:"/login",children:"Log In"})]}),B?Object(p.jsx)(j.a,{}):""]})}},43:function(n,e,t){"use strict";var a=t(40),r=t.n(a);e.a=function(n){return r.a.get(n,{withCredentials:!0}).then((function(n){return n.data.data}))}},49:function(n,e,t){"use strict";var a=t(37),r=t(0);e.a=function(n){var e=Object(r.useState)(n),t=Object(a.a)(e,2),o=t[0],c=t[1];return[o,Object(r.useCallback)((function(n){c(n.target.value)}),[]),c]}},50:function(n,e,t){"use strict";t.d(e,"d",(function(){return j})),t.d(e,"c",(function(){return p})),t.d(e,"f",(function(){return x})),t.d(e,"e",(function(){return h})),t.d(e,"a",(function(){return g})),t.d(e,"b",(function(){return f})),t.d(e,"g",(function(){return O}));var a,r,o,c,i,s,b,d,l=t(45),u=t(47),j=u.a.header(a||(a=Object(l.a)(["\n  text-align: center;\n  font-family: Slack-Larsseit, Helvetica Neue, Helvetica, Segoe UI, Tahoma, Arial, sans-serif;\n  font-weight: 700;\n  font-size: 48px;\n  line-height: 46px;\n  letter-spacing: -0.75px;\n  margin-top: 50px;\n  margin-bottom: 50px;\n"]))),p=u.a.form(r||(r=Object(l.a)(["\n  margin: 0 auto;\n  width: 400px;\n  max-width: 400px;\n"]))),x=u.a.label(o||(o=Object(l.a)(["\n  margin-bottom: 16px;\n\n  & > span {\n    display: block;\n    text-align: left;\n    padding-bottom: 8px;\n    font-size: 15px;\n    cursor: pointer;\n    line-height: 1.46666667;\n    font-weight: 700;\n  }\n"]))),h=u.a.input(c||(c=Object(l.a)(["\n  border-radius: 4px;\n  --saf-0: rgba(var(--sk_foreground_high_solid, 134, 134, 134), 1);\n  border: 1px solid var(--saf-0);\n  transition: border 80ms ease-out, box-shadow 80ms ease-out;\n  box-sizing: border-box;\n  margin: 0 0 20px;\n  width: 100%;\n  color: rgba(var(--sk_primary_foreground, 29, 28, 29), 1);\n  background-color: rgba(var(--sk_primary_background, 255, 255, 255), 1);\n  padding: 12px;\n  height: 44px;\n  padding-top: 11px;\n  padding-bottom: 13px;\n  font-size: 18px;\n  line-height: 1.33333333;\n\n  &:focus {\n    --saf-0: rgba(var(--sk_highlight, 18, 100, 163), 1);\n    box-shadow: 0 0 0 1px var(--saf-0), 0 0 0 5px rgba(29, 155, 209, 0.3);\n  }\n"]))),g=u.a.button(i||(i=Object(l.a)(["\n  margin-bottom: 12px;\n  width: 100%;\n  max-width: 100%;\n  color: #fff;\n  background-color: #14a0a0;\n  border: none;\n  font-size: 18px;\n  font-weight: 900;\n  height: 44px;\n  min-width: 96px;\n  padding: 0 16px 3px;\n  transition: all 80ms linear;\n  user-select: none;\n  outline: none;\n  cursor: pointer;\n  border-radius: 4px;\n  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.3);\n\n  &:hover {\n    background-color: rgba(40, 180, 180, 0.9);\n    border: none;\n  }\n\n  &:focus {\n    --saf-0: rgba(var(--sk_highlight, 18, 100, 163), 1);\n    box-shadow: 0 0 0 1px var(--saf-0), 0 0 0 5px rgba(29, 155, 209, 0.3);\n  }\n"]))),f=u.a.div(s||(s=Object(l.a)(["\n  color: #e01e5a;\n  margin: 0px 0 16px;\n  // font-weight: bold;\n"]))),O=(u.a.div(b||(b=Object(l.a)(["\n  color: #2eb67d;\n  font-weight: bold;\n"]))),u.a.p(d||(d=Object(l.a)(["\n  font-size: 13px;\n  color: #616061;\n  margin: 0 auto 8px;\n  width: 400px;\n  max-width: 400px;\n\n  & a {\n    color: #1264a3;\n    text-decoration: none;\n    font-weight: 700;\n\n    &:hover {\n      text-decoration: underline;\n    }\n  }\n"]))))},51:function(n,e,t){"use strict";var a=t(149),r=t(154),o=t(138),c=(t(0),t(6)),i=Object(o.a)((function(n){return{backdrop:{zIndex:n.zIndex.drawer+1,color:"#fff"}}}));e.a=function(){var n=i();return Object(c.jsx)(a.a,{className:n.backdrop,open:!0,children:Object(c.jsx)(r.a,{color:"inherit"})})}}}]);
//# sourceMappingURL=9.342ae4dd.chunk.js.map