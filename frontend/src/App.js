import './App.css';
import Login from './pages/login/Login';
import {BrowserRouter as Router,Routes,Route} from 'react-router-dom';
import Register from './pages/register/Register';
import Home from './pages/home/Home';
import Cart from './pages/cart/Cart';
import Profile from './pages/profile/Profile';
function App() {
  return (
    <div className="App">
      <Router>
          <Routes>
            <Route path='/' element={<Login/>}></Route>
            <Route path='/register' element={<Register/>}></Route>
            <Route path="/home" element={<Home/>}/>
            <Route path="/cart" element={<Cart/>}/>
            <Route path="/profile" element={<Profile/>}/>
          </Routes> 
      </Router>
    </div>
  );
}

export default App;
