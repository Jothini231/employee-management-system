import React from "react";
import SideBar from "./components/SideBar";
import { BrowserRouter, Route, Routes } from "react-router-dom";

// Pages
import Dashboard from "./pages/dashboard/Dashboard";
import Employees from "./pages/employees/Employees";
import Department from "./pages/departments/Department";

// Employee Components
import EmployeeProfile from "./components/employee/EmployeeProfile";
import EmployeeForm from "./components/employee/EmployeeForm";
import Leave from "./pages/leaves/Leave";
import ApplyLeave from "./components/Leave/ApplyLeave";
import Salary from "./pages/salaries/Salary";
import AddEditSalaryPage from "./components/salary/AddEditSalary";
import Settings from "./pages/settings/Settings";

const App = () => {
  return (
    <BrowserRouter>
      <div className="flex">
        <SideBar />

        <div className="ml-64 flex-1 p-6">
          <Routes>
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
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
};

export default App;