const toast=document.getElementById("toast");let toastTimer;
function showToast(message){toast.textContent=message;toast.classList.add("show");clearTimeout(toastTimer);toastTimer=setTimeout(()=>toast.classList.remove("show"),2600);}
document.querySelectorAll("[data-dashboard-patient]").forEach(button=>button.addEventListener("click",()=>{const name=button.dataset.dashboardPatient;const url=new URL("nova-avaliacao.html",window.location.href);url.searchParams.set("patient",name);window.location.href=url.toString();}));
document.getElementById("exportReport")?.addEventListener("click",()=>window.print());
document.getElementById("dashboardRange")?.addEventListener("change",event=>showToast(`Período selecionado: ${event.target.value}. Os indicadores exibidos são demonstrativos.`));
document.querySelectorAll(".chart-tabs span").forEach(tab=>{tab.setAttribute("role","button");tab.setAttribute("tabindex","0");const select=()=>{tab.parentElement.querySelectorAll("span").forEach(item=>item.classList.remove("selected"));tab.classList.add("selected");showToast(`Indicador selecionado: ${tab.textContent}. Gráfico demonstrativo.`);};tab.addEventListener("click",select);tab.addEventListener("keydown",e=>{if(e.key==="Enter"||e.key===" "){e.preventDefault();select();}});});


