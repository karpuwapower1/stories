import axios from "axios";

export const jwtToken = localStorage.getItem("authorization");

axios.interceptors.request.use(
  function (config) {
    if (jwtToken) {
      config.headers.Authorization = `Bearer ${jwtToken}`;
      config.headers["Content-Type"]="application/json";
    }
    return config;
  },
  function (err) {
    return Promise.reject(err);
  }
);