import React, { SyntheticEvent } from "react";
import { useState } from "react";
import { Redirect } from "react-router";

const Register = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [redirect, setRedirect] = useState(false);
  const submit = async (e: SyntheticEvent) => {
    e.preventDefault();

   await fetch("http://localhost:8080/api/v1/signup", {
      method: 'POST',
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        username,
        email,
        password,
      }),
    });
    setRedirect(true);
  };
  if (redirect) {
    return <Redirect to='/login' />;
  }
  return (
    <form onSubmit={submit}>
      <h1 className='h3 mb-3 fw-normal'>Please Register</h1>

      <input
        type='username'
        className='form-control'
        placeholder='ID'
        onChange={(e) => setUsername(e.target.value)}
        required
      />
      <input
        type='email'
        className='form-control'
        placeholder='Email'
        onChange={(e) => setEmail(e.target.value)}
        required
      />

      <input
        type='password'
        className='form-control'
        placeholder='Password'
        onChange={(e) => setPassword(e.target.value)}
        required
      />

      <button className='w-100 btn btn-lg btn-primary' type='submit'>
        Submit
      </button>
    </form>
  );
};

export default Register;
