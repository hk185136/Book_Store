import './App.css';
import Login from './pages/login/Login';
import {BrowserRouter as Router,Routes,Route} from 'react-router-dom';
import Register from './pages/register/Register';
import Home from './pages/home/Home';
import Cart from './pages/cart/Cart';
import Profile from './pages/profile/Profile';
import PrivateRoute from './PrivateRoute';
import Books from './components/booklist/Books';
import { useState ,useEffect} from 'react';
import Orders from './pages/orders/Orders';
import axios from 'axios';
import Users from './pages/OrderManagement/Users';
import { useSelector } from 'react-redux';
import { toast } from 'react-toastify';
function App() {
  const [books,setBooks] = useState([]);
  const user = useSelector(state=>state);
  const [cartItems,setCartItems] = useState([]);
  useEffect(()=>{
    async function getAllBooks(){
      try{
        const res = await axios.get('http://localhost:8080/api/user/books/');
        // console.log(res.data);
        setBooks(res.data);
      }
      catch(e){
        toast.error((e?.response?.data?.message) || (e.message));
      }
    }
    async function getCartItems(){
      try{
        console.log("searching by the name : "+user.name)
        const res = await axios.put('http://localhost:8080/api/item/searchByStatus/added to cart',{username : user.name});
        if(res.status==200){
          // console.log('useeffect runing')
          // console.log(res.data)
          setCartItems(res.data);
        }
      }
      catch(e){
        toast.error((e?.response?.data?.message) || (e.message));
      }
      
    }
    if(user && user.token && user.token!=''){
      getAllBooks();
      getCartItems();
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
                <Route path='/home' element={<Books books = {books} setBooks = {setBooks} cartItems = {cartItems} setCartItems = {setCartItems}  />}/>
                <Route path="/home/cart" element={<Cart cartItems = {cartItems} setCartItems = {setCartItems}  />}/>
                <Route path="/home/profile" element={<Profile/>}/>
                <Route path='/home/orders' element={<Orders/>}/>
                <Route path='/home/users' element={<Users/>}/>
              </Route>
              
            </Route>
            
          </Routes> 
      </Router>
    </div>
  );
}

export default App;
