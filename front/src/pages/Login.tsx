import React, { SyntheticEvent } from "react";
import { useState } from "react";
import Axios from "axios";
import { Redirect } from "react-router";
const config = {
  headers: {
    "Content-Type": "application/json; charset=utf-8",
  },
};
const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [redirect, setRedirect] = useState(false);
  const submit = async (e: SyntheticEvent) => {
    e.preventDefault(); // const submit = async (e: SyntheticEvent) => {
    e.preventDefault();
    console.log(1);
    let jwtToken = await Axios.post(
      "http://localhost:8080/api/v1/auth",
      JSON.stringify({
        username,
        password,
      }),
      config
    );
    console.log(1, jwtToken);
    if (jwtToken.status === 200) {
      console.log(1, jwtToken.data);
      localStorage.setItem("jwtToken", jwtToken.data.token);
      setRedirect(true);
    }
  };
  if(redirect){
    return <Redirect to="/"/>
  }

  return (
    <form onSubmit={submit}>
      <h1 className='h3 mb-3 fw-normal'>Please sign in</h1>

      <input
        type='username'
        className='form-control'
        placeholder='ID'
        required
        onChange={(e) => setUsername(e.target.value)}
      />

      <input
        type='password'
        className='form-control'
        placeholder='Password'
        onChange={(e) => setPassword(e.target.value)}
        required
      />

      <button className='w-100 btn btn-lg btn-primary' type='submit'>
        Sign in
      </button>
    </form>
  );
};

export default Login;
