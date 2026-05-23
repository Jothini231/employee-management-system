import api from "./api";

const API = "http://localhost:8080/api/salaries";

export const getAllSalaries = () => api.get(API);

export const addSalary = (salaryData) => api.post(API,salaryData);

export const updateSalary = (id , salaryData) =>api.put(`${API}/${id}`,salaryData);

export const deleteSalary = (id) => api.delete(`${API}/${id}`);

export const getSalaryById = (id) => api.get(`${API}/${id}`);

export const filterSalaries = (month, departmentId, employeeId) => {
  const params = new URLSearchParams();

  if (month) params.append("month", month);
  if (departmentId) params.append("departmentId", departmentId);
  if (employeeId) params.append("employeeId", employeeId);

  return api.get(`${API}/filter?${params.toString()}`);
};

export const searchSalaries = (keyword) =>
  api.get(`${API}/search`, {
    params: { keyword },
  });
