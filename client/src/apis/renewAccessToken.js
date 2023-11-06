import { getCookie } from "../utils/cookie";
import axios from "axios";

export const renewAccessToken = async () => {
  const AccessToken = localStorage.getItem('AccessToken')
  const RefreshToken = getCookie("RefreshToken");
  const headers = {
    Authorization: "Bearer " + AccessToken,
  };
  const result = await axios.post(
    "https://wishme.co.kr/api/users/refresh",
    { headers },
    {
      AccessToken,
      RefreshToken,
    }
  );

  return result;
};