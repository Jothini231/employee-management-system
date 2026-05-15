import axios from "axios";

const API = "http://localhost:8080/api/salaries";

export const getAllSalaries = () => axios.get(API);

export const addSalary = (salaryData) => axios.post(API,salaryData);

export const updateSalary = (id , salaryData) =>axios.put(`${API}/${id}`,salaryData);

export const deleteSalary = (id) => axios.delete(`${API}/${id}`);

export const getSalaryById = (id) => axios.get(`${API}/${id}`);

export const filterSalaries = (month, departmentId, employeeId) => {
  const params = new URLSearchParams();

  if (month) params.append("month", month);
  if (departmentId) params.append("departmentId", departmentId);
  if (employeeId) params.append("employeeId", employeeId);

  return axios.get(`${API}/filter?${params.toString()}`);
};

export const searchSalaries = (keyword) =>
  axios.get(`${API}/search`, {
    params: { keyword },
  });