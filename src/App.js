import React, { useState, useEffect } from "react";

function App() {
  const [companyProfit, setCompanyProfit] = useState(null);
  const [statistics, setStatistics] = useState([]);
  const [salesl2l3, setSalesl2l3] = useState([]);
  const [products, setProducts] = useState([]);

  useEffect(() => {
    // company stats
    const eventSource = new EventSource(
      "http://localhost:8080/company/statistics"
    );

    eventSource.onopen = () => {
      console.log("Connection to SSE established.");
    };

    eventSource.onmessage = (event) => {
      const data = JSON.parse(event.data);
      setStatistics(data);
    };

    eventSource.onerror = (error) => {
      console.error("SSE error:", error);
      eventSource.close();
    };

    return () => {
      eventSource.close();
    };
  }, []);

  useEffect(() => {
    // salesL2L3 stats
    const eventSource = new EventSource(
      "http://localhost:8080/company/salesL2andL3"
    );

    eventSource.onopen = () => {
      console.log("Connection to SSE established.");
    };

    eventSource.onmessage = (event) => {
      const data = JSON.parse(event.data);
      setSalesl2l3(data);
    };

    eventSource.onerror = (error) => {
      console.error("SSE error:", error);
      eventSource.close();
    };

    return () => {
      eventSource.close();
    };
  }, []);

  useEffect(() => {
    // sales products stats
    const eventSource = new EventSource(
      "http://localhost:8080/productStatistics"
    );

    eventSource.onopen = () => {
      console.log("Connection to SSE established.");
    };

    eventSource.onmessage = (event) => {
      const data = JSON.parse(event.data);
      setProducts(data);
    };

    eventSource.onerror = (error) => {
      console.error("SSE error:", error);
      eventSource.close();
    };

    return () => {
      eventSource.close();
    };
  }, []);

  const formatValue = (value) => {
    value = value.toFixed(2);

    return parseFloat(value).toLocaleString();
  };

  return (
    <div id="wrapper">
      <div id="content-wrapper" class="d-flex flex-column" className="p-4">
        <div id="content">
          <div class="container-fluid">
            {/* Page Heading */}
            <div class="d-sm-flex align-items-center justify-content-between mb-4">
              <h1 class="h3 mb-0 text-gray-800">
                Insurance Company Statistics
              </h1>
            </div>
            {/* <div class="row"> */}
            {/* Earnings (Monthly) Card Example  */}
            <div className="row">
              <div className="col-lg-6">
                {Object.entries(statistics).map(([key, value]) => (
                  <div class=" mb-4" key={key}>
                    {key === "companyProfit" ? (
                      <div className="">
                        <div className="card border-left-primary shadow h-100 py-2">
                          <div className="card-body">
                            <div className="row no-gutters align-items-center">
                              <div className="col mr-2">
                                <div className="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                  Company Profit
                                </div>
                                <div className="h5 mb-0 font-weight-bold text-gray-800">
                                  {formatValue(value)}лв
                                </div>
                              </div>
                              <div className="col-auto">
                                <i className="fas fa-calendar fa-2x text-gray-300"></i>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    ) : null}

                    {key === "numberOfSales" ? (
                      <div class="  mb-4">
                        <div class="card border-left-warning shadow h-100 py-2">
                          <div class="card-body">
                            <div class="row no-gutters align-items-center">
                              <div class="col mr-2">
                                <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                  Number of Sales
                                </div>
                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                  {value}
                                </div>
                              </div>
                              <div class="col-auto">
                                <i class="fas fa-comments fa-2x text-gray-300"></i>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    ) : null}

                    {key === "premiumIncome" ? (
                      <div class="mb-4">
                        <div class="card border-left-success shadow h-100 py-2">
                          <div class="card-body">
                            <div class="row no-gutters align-items-center">
                              <div class="col mr-2">
                                <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                  Premium Income
                                </div>
                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                  {formatValue(value)}лв
                                </div>
                              </div>
                            </div>
                            <div class="col-auto">
                              <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                            </div>
                          </div>
                        </div>
                      </div>
                    ) : null}
                  </div>
                ))}
              </div>

              <div className="col-lg-6">
                <div>
                  {/* Default Card Example --> */}
                  <div className="card mb-4">
                    <div className="card-body">
                      <table className="table">
                        <thead>
                          <tr>
                            <th>Product Title</th>

                            <th>Saled products count</th>
                          </tr>
                        </thead>
                        <tbody>
                          {Object.entries(products).map(
                            ([productTitle, products]) => (
                              <tr key={[productTitle]}>
                                <td>{productTitle}</td>

                                <td>{products}</td>
                              </tr>
                            )
                          )}
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          {/* <div class="row">
            <ul>
              {Object.entries(statistics).map(([key, value]) => (
                <li key={key}>
                  {key}: {value}
                </li>
              ))}
            </ul>
          </div> */}

          <div class="row">
            <div class="col-lg-12">
              {/* Default Card Example --> */}
              <div className="card mb-4">
                <div className="card-body">
                  <table className="table">
                    <thead>
                      <tr>
                        <th>Sale Number</th>

                        <th>Intermediary Title</th>
                        <th>Product Title</th>
                        <th>Premium Calculated</th>
                        <th>Commission Calculated</th>
                        <th>Sales Date</th>
                        <th>Company Profit</th>
                      </tr>
                    </thead>
                    <tbody>
                      {Object.entries(salesl2l3).map(
                        ([saleNumber, saleData]) => (
                          <tr key={saleNumber}>
                            <td>{saleNumber}</td>

                            <td>{saleData.intermediaryTitle}</td>
                            <td>{saleData.productTitle}</td>
                            <td>{formatValue(saleData.premiumCalcuated)}</td>
                            <td>
                              {formatValue(saleData.commissionCalculated)}
                            </td>
                            <td>
                              {new Date(saleData.salesDate).toLocaleString()}
                            </td>
                            <td>{formatValue(saleData.companyProfit)}</td>
                          </tr>
                        )
                      )}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
