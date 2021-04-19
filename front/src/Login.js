// import React from "react";
// import { GoogleLogin } from "react-google-login";
// import Axios from "axios";

// const config = {
//   headers: {
//     "Content-Type": "application/json; charset=utf-8",
//   },
// };

// const responseGoogle = async (response) => {
//   console.log(1, response);
//   let jwtToken = await Axios.post(
//     "http://localhost:8080/api/v1/oauth/google",
//     JSON.stringify(response),
//     config
//   );
//   if (jwtToken.status === 200) {
//     console.log(2, jwtToken.data.data.token);
//     localStorage.setItem("jwtToken", jwtToken.data.data.token);
//   }
// };

// const Login = () => {
//   return (
//     <GoogleLogin
//       clientId="588000139755-i5inq6jei66hsugf2ilef0bqu4ev7rns.apps.googleusercontent.com"
//       buttonText="Login"
//       onSuccess={responseGoogle}
//       onFailure={responseGoogle}
//       cookiePolicy={"single_host_origin"}
//     />
//   );
// };

// export default Login;