import axios from "axios"
import { toast } from "react-toastify";

export const getHistory = async (username)=>{
    try{
        console.log("called getHistory")
        const res = await axios.get(`http://localhost:8080/api/user/orderhistory/getOrderHistory/${username}`);
        console.log(res.data);
        return res.data;
    }
    catch(e){
        toast.error((e?.response?.data?.message) || (e.message));
    }
}