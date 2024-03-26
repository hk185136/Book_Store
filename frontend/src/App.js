import './App.css';
import Login from './pages/login/Login';
import {BrowserRouter as Router,Routes,Route} from 'react-router-dom';
import Register from './pages/register/Register';
import Home from './pages/home/Home';
import Cart from './pages/cart/Cart';
import Profile from './pages/profile/Profile';
import PrivateRoute from './PrivateRoute';
import Books from './components/booklist/Books';
import { useState ,useEffect, useContext} from 'react';
import { userContext } from './UserContext';
import axios from 'axios';
function App() {
  const [books,setBooks] = useState([]);
  const [user,setUser] = useContext(userContext);
  if(!user){
    setUser(localStorage.getItem('user'));
  }

  useEffect(()=>{
    async function getAllBooks(){
      try{
        const res = await axios.get('http://localhost:8080/api/user/books/');
        console.log(res.data);
        setBooks(res.data);
      }
      catch(e){
        alert(e.message);
      }
    }
    getAllBooks();
  },[])
  useEffect(()=>{
    console.log(user);
    console.log(localStorage.getItem('user'))
    if(!user || !user.token){
      console.log("enetered the useeffect if statement")
      setUser(JSON.parse(localStorage.getItem('user')));
    }
  },[user])

  return (
    <div className="App">
      <Router>
          <Routes>
            <Route path='/' element={<Login/>}></Route>
            <Route path='/register' element={<Register/>}></Route>
            <Route element={<PrivateRoute/>}>
              <Route path="/home" element={<Home books = {books} setBooks = {setBooks} />}>
                <Route path='/home' element={<Books books = {books} setBooks = {setBooks}  />}/>
                <Route path="/home/cart" element={<Cart/>}/>
                <Route path="/home/profile" element={<Profile/>}/>
              </Route>
              
            </Route>
            
          </Routes> 
      </Router>
    </div>
  );
}

export default App;
