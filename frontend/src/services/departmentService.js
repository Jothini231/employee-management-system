import api from "./api";

const API = "http://localhost:8080/api/departments";

export const getDepartments = () => api.get(API);

export const getDepartmentById = (id) => api.get(`${API}/${id}`);

export const createDepartment = (data) => api.post(API,data);

export const updateDepartment = (id,data) => api.put(`${API}/${id}`,data);

export const deleteDepartment = (id) => api.delete(`${API}/${id}`);

export const assignManager = (deptId,managerId) => api.put(`${API}/${deptId}/manager/${managerId}`);
