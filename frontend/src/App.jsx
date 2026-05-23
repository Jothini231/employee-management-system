import React, { useContext } from "react";
import SideBar from "./components/SideBar";

import { BrowserRouter, Route, Routes, Navigate, Outlet } from "react-router-dom";

// Pages
import Dashboard from "./pages/dashboard/Dashboard";
import Employees from "./pages/employees/Employees";
import Department from "./pages/departments/Department";
import Login from "./pages/login/Login";
import Register from "./pages/register/Register";

// Employee Components
import EmployeeProfile from "./components/employee/EmployeeProfile";
import EmployeeForm from "./components/employee/EmployeeForm";
import Leave from "./pages/leaves/Leave";
import ApplyLeave from "./components/Leave/ApplyLeave";
import Salary from "./pages/salaries/Salary";
import AddEditSalaryPage from "./components/salary/AddEditSalary";
import Settings from "./pages/settings/Settings";


import { AuthProvider, AuthContext } from "./context/AuthContext";

const ProtectedRoute = ({ children }) => {
  const { user, loading } = useContext(AuthContext);
  if (loading) return <div className="flex items-center justify-center min-h-screen">Loading...</div>;
  if (!user) return <Navigate to="/login" replace />;
  return children;
};

const MainLayout = () => {
  return (
    <div className="flex bg-gray-50 min-h-screen">
      <SideBar />
      <div className="ml-64 flex-1 flex flex-col min-h-screen">
        <div className="p-6 flex-1">
          <Outlet />
        </div>
      </div>
    </div>
  );
};

const App = () => {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          
          <Route element={<ProtectedRoute><MainLayout /></ProtectedRoute>}>
            <Route path="/" element={<Dashboard />} />
            <Route path="/employees" element={<Employees />} />
            <Route path="/employees/add" element={<EmployeeForm />} />
            <Route path="/employees/edit/:id" element={<EmployeeForm />} />
            <Route path="/employees/view/:id" element={<EmployeeProfile />} />

            <Route path="/departments" element={<Department />} />

            <Route path="/leave" element={<Leave />} />
            <Route path="/apply-leave" element={<ApplyLeave/>} />

            <Route path="/salary" element={<Salary />} />
            <Route path="/salaries/add" element={<AddEditSalaryPage />} />
            <Route path="/salaries/edit/:id" element={<AddEditSalaryPage />} />
            <Route path="/settings" element={<Settings />} />
          </Route>
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
};

export default App;