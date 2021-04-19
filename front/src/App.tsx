import React from "react";
import "./App.css";
import Login from "./pages/Login";
import Nav from "./components/Nav";
import { BrowserRouter, Route } from "react-router-dom";
import Home from "./pages/Home";
import Register from "./pages/Register";
// import Login from "./Login";
// import Axios from "axios";

// const config = {
//   headers: {
//     Authorization: "Bearer " + localStorage.getItem("jwtToken"),
//   },
// };

// function App() {
//     const [users, setUser] = useState('');
//     const getUser = async () => {
//       let res = await Axios.get("http://localhost:8080/api/v1/user/me", config);
//       setUser(res.data.data);
//     };

//     console.log(1, users);

//     return (
//       <div>
//         <Login />
//         <h1>
//           /user ::
//           {
//           Object.keys(users).map((key, i) =>(
//             <div key={i}>
//               {users[key]}</div>)
//       )}
//         </h1>
//         <button
//           onClick={() => {
//             getUser();
//           }}
//         >
//           유저정보 가져오기
//         </button>
//       </div>
//     );
//   }

//   export default App;

function App() {
  return (
    <div className='App'>
      <BrowserRouter>
        <Nav />
        <main className='form-signin'>
          <Route path='/' exact component={Home}></Route>
          <Route path='/login' component={Login}></Route>
          <Route path='/register' component={Register}></Route>
        </main>
      </BrowserRouter>
    </div>
  );
}

export default App;
