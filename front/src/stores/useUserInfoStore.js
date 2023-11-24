import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";
import axios from "axios";

export const useUserInfoStore = defineStore("userInfo", () => {
  const URL = {
    1: "http://localhost/vue",
    2: "http://192.168.0.2/vue",
    3: "http://192.168.31.75/vue",
  };
  const isLoginCheck = async () => {
    const token = localStorage.accessToken;
    console.log(token);
    if (!token) {
      return false;
    }
    let res = true;
    const userId = decodedToken(token);
    console.log(userId);
    res = await _accessTokenCheck(userId);
    return res;
  };

  const decodedToken = (token) => {
    const decoded = jwtDecode(token);
    return decoded.userId;
  };

  const getUserId = async () => {
    if (!localStorage.accessToken) return "";
    const token = localStorage.accessToken;
    const chkRes = await _accessTokenCheck(token);
    if (!chkRes) return "";
    return decodedToken(token);
  };

  const _accessTokenCheck = async (userId) => {
    if (!localStorage.accessToken) return false;
    let accessToken = localStorage.accessToken;
    try {
      await axios.get(`${URL[3]}/user/info/${userId}`, {
        headers: {
          Authorization: accessToken,
        },
      });
    } catch (err) {
      if (err.response.status === 401) {
        localStorage.removeItem("accessToken");
        return false;
      }
    }
    return true;
  };

  const loginProcess = (token) => {
    localStorage.setItem("accessToken", token);
  };

  return {
    loginProcess,
    decodedToken,
    isLoginCheck,
    getUserId,
  };
});
