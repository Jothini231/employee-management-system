import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { deleteSalary, filterSalaries, getAllSalaries, searchSalaries } from "../../services/salaryService";
import { getEmployees } from "../../services/employeeService";
import { getDepartments } from "../../services/departmentService";
import SalaryFilters from "../../components/salary/SalaryFilters";
import {
  FaWallet,
  FaDollarSign,
  FaCalendarAlt,
  FaChartBar,
  FaSearch,
  FaPlus,
} from "react-icons/fa";
import SalaryTable from "../../components/salary/SalaryTable";
import DeleteModal from "../../components/common/DeleteModel";
function SalaryPage () {

    const navigate = useNavigate();
    const [salaries,setSalaries] = useState([]);
    const [employees,setEmployees] = useState([]);
    const [departments,setDepartments] = useState([]);

    const [keyword, setKeyword] = useState("");
    const [month,setMonth] = useState ("");
    const [departmentId,setDepartmentId] = useState("");
    const [employeeId,setEmployeeId] = useState("");
    const [deleteSalaryId , setDeleteSalaryId] = useState("");

    const months = [...new Set(salaries.map((s) => s.month).filter(Boolean))];

    useEffect(() =>{
        fetchData();
    },[]);


    useEffect(() => {
    
        if (!month && !departmentId && !employeeId) return;
        handleFilter();
    
    }, [month, departmentId, employeeId]);

    const fetchData = async () => {
        try{
            const [salariesRes , empRes , deptRes] = await Promise.all([
                getAllSalaries(),
                getEmployees(),
                getDepartments(),
            ]);
            setSalaries(salariesRes.data.data || []);
            setEmployees(empRes.data.data || []);
            setDepartments(deptRes.data.data || []);
        }catch(error){
            console.log("Error in fetching data : " ,error);
        }
    }

    const handleSearch = async () => {
        try{
            const res = await searchSalaries(keyword);
            setSalaries(res.data.data || [])
        }catch(error){
            console.log("Error in searching keyword : ",error)
        }
    }

    const handleFilter =async () => {
        try{
            const res = await filterSalaries(month, departmentId, employeeId);
            console.log(res.data);
            setSalaries(res.data.data || []);
        }catch(error){
            console.log("Error in filtering salaries : ",error)
        }
    }

    const handleReset = () => {
        setMonth("");
        setDepartmentId("");
        setEmployeeId("");
        fetchData();
    }

    const handleDelete = async (deleteSalaryId) => {
            try{
                await deleteSalary(deleteSalaryId);
                await fetchData();
                closeDeleteModal();
            }catch(error){
                console.log(error);
            }
    }

    const closeDeleteModal = () => {
        setDeleteSalaryId("");
    }

    const formatCurrency = (amount) => {
     return new Intl.NumberFormat("en-LK", {
        style: "currency",
        currency: "LKR",
        maximumFractionDigits: 0,
    }).format(amount || 0);
    };     
    
    const currentMonth = new Date().toLocaleString("en-US", { month: "long", year: "numeric" });

// use selected filter month if active, otherwise use current month
const activePeriod = month || currentMonth;

const currentMonthSalaries = salaries.filter((s) => s.month === activePeriod);

const totalSalaries = salaries.length;

const totalPayout = currentMonthSalaries.reduce(
    (acc, curr) => acc + (curr.netSalary || 0), 0
);

const avgSalary = currentMonthSalaries.length > 0
    ? totalPayout / currentMonthSalaries.length
    : 0;
    return (

        <div className="p-6 bg-slate-50 min-h-screen font-sans">
              
            <div className="flex justify-between items-center mb-6">
            <h1 className="text-2xl font-bold text-gray-800">Salary Management</h1>
            <button
                onClick={() => navigate("/salaries/add")}
                className="bg-[#1E2A38] hover:bg-[#2C3A4D] text-white px-4 py-2 rounded-lg flex items-center gap-2 transition"
            >
                <FaPlus className="text-sm" /> Add Salary
            </button>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
                <div className="bg-white rounded-xl shadow-sm p-6 border border-gray-100 flex items-center justify-between">                   
                    <div>
                        <p className="text-sm font-medium text-gray-500 mb-1">Total Salaries</p>
                        <h3 className="text-2xl font-bold text-gray-800">{totalSalaries}</h3>
                    </div>

                    <div className="bg-blue-100 p-3 rounded-full text-blue-600">
                        <FaWallet size={24} />
                    </div>
                </div>

                <div className="bg-white rounded-xl shadow-sm p-6 border border-gray-100 flex items-center justify-between">
                    <div>
                        <p className="text-sm font-medium text-gray-500 mb-1">Total Payout</p>
                        <h3 className="text-2xl font-bold text-gray-800">{formatCurrency(totalPayout)}</h3>
                    </div>

                    <div className="bg-green-100 p-3 rounded-full text-green-600">
                        <FaDollarSign size={24} />
                    </div>
                </div>

                <div className="bg-white rounded-xl shadow-sm p-6 border border-gray-100 flex items-center justify-between">
                    <div>
                        <p className="text-sm font-medium text-gray-500 mb-1">Payout Period</p>
                        <h3 className="text-2xl font-bold text-gray-800">{activePeriod}</h3>
                    </div>

                    <div className="bg-purple-100 p-3 rounded-full text-purple-600">
                        <FaCalendarAlt size={24} />
                    </div>
                </div>

                <div className="bg-white rounded-xl shadow-sm p-6 border border-gray-100 flex items-center justify-between">
                    <div>
                        <p className="text-sm font-medium text-gray-500 mb-1">Average Salary</p>
                        <h3 className="text-2xl font-bold text-gray-800">{formatCurrency(avgSalary)}</h3>
                    </div>

                    <div className="bg-orange-100 p-3 rounded-full text-orange-600">
                        <FaChartBar size={24} />
                    </div>
                </div>

            </div>

            <SalaryFilters 
                    months={months}
                    month = {month}
                    setMonth = {setMonth}
                    departmentId = {departmentId}
                    setDepartmentId = {setDepartmentId}
                    departments = {departments}
                    employeeId = {employeeId}
                    setEmployeeId = {setEmployeeId}
                    employees = {employees}
                    handleSearch = {handleSearch}
                    handleReset = {handleReset}
            />

            <SalaryTable 
                    salaries={salaries}
                    employees={employees}
                    departments={departments}
                    formatCurrency={formatCurrency}
                    setDeleteSalaryId={setDeleteSalaryId}
                    navigate={navigate}
            />

            {deleteSalaryId && (
                <DeleteModal 
                    name="this salary record"
                    onCancel={closeDeleteModal}
                    onConfirm={() => handleDelete(deleteSalaryId)}
                
                />)}
        </div>

    );
    }

export default SalaryPage;

// to do : filtering not working check it when it selected for filter table not updated