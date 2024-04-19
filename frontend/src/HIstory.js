import axios from "axios"
import { toast } from "react-toastify";

export const getHistory = async (username)=>{
    try{
        // Getting history of current user.
        const res = await axios.get(`http://localhost:8080/api/user/orderHistory/getOrderHistory/${username}`);
        return res.data;
    }
    catch(e){
        toast.error((e?.response?.data?.message) || (e.message));
    }
}