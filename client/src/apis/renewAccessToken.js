import axios from "axios";

export const renewAccessToken = async () => {
  const RefreshToken = localStorage.getItem("RefreshToken");
  const headers = {
    RefreshToken: RefreshToken,
  };
  const result = await axios.post(
    "https://wishme.co.kr/api/users/refresh",
    { headers }
  );

  return result;
};