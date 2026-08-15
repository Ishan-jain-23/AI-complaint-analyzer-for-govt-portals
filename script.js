let screen = "home";
let complaint = {};

function render(){
  document.getElementById('app').innerHTML = views[screen]();
}

function go(s){
  screen = s;
  render();
}

function stepper(current){
  let html = '<div class="stepper">';
  for(let i=1;i<=3;i++){
    let cls = i===current ? 'active' : (i<current ? 'done' : '');
    html += `<div class="step-circle ${cls}">${i}</div>`;
  }
  html += '</div>';
  return html;
}

function genComplaintId(){
  return "GRV-2026-" + Math.floor(10000 + Math.random()*89999);
}

function fakeCategoryFromText(text){
  const t = text.toLowerCase();
  if (t.includes('bus') || t.includes('road') || t.includes('transport')) return {cat:'Public Transport', dept:'Transport Dept'};
  if (t.includes('water')) return {cat:'Water Supply', dept:'Water Board'};
  if (t.includes('electric') || t.includes('power')) return {cat:'Electricity', dept:'Electricity Board'};
  if (t.includes('garbage') || t.includes('sanitation') || t.includes('waste')) return {cat:'Sanitation', dept:'Sanitation Dept'};
  if (t.includes('bribe') || t.includes('corrupt')) return {cat:'Corruption', dept:'Vigilance Cell'};
  return {cat:'General', dept:'General Administration'};
}

const views = {};

views.home = () => `
  <div class="hero">
    <h1>Your Voice. Our Responsibility.</h1>
    <p>Submit your complaint and let AI automatically analyze, categorize and route it to the appropriate government department.</p>
    <div class="btn-row">
      <button class="btn-primary" onclick="go('details')">File a Complaint</button>
      <button class="btn-outline" onclick="go('track')">Track Complaint</button>
    </div>
  </div>
  <div class="feature-grid">
    <div class="feature-card"><div class="feature-icon">🧠</div><h3>AI Complaint Analysis</h3><p>Automatically understands and categorizes complaints.</p></div>
    <div class="feature-card"><div class="feature-icon">📍</div><h3>Smart Department Routing</h3><p>Routes complaints to the right department.</p></div>
    <div class="feature-card"><div class="feature-icon">📊</div><h3>Real-Time Tracking</h3><p>Track status with a unique complaint ID.</p></div>
    <div class="feature-card"><div class="feature-icon">⚠️</div><h3>Priority Detection</h3><p>AI flags urgent and high-priority complaints.</p></div>
  </div>
  <div class="stats-row">
    <div class="stat"><b>24×7</b><span>Available Anytime</span></div>
    <div class="stat"><b>🔒</b><span>Secure & Confidential</span></div>
    <div class="stat"><b>🤖</b><span>AI-Assisted Processing</span></div>
  </div>
`;

views.details = () => `
  ${stepper(1)}
  <div class="form-card">
    <h2>Your Details</h2>
    <p class="muted">We need this to identify your complaint.</p>
    <label>Full Name *</label>
    <input id="name" value="${complaint.name||''}" placeholder="Enter full name">
    <label>Preferred Language</label>
    <select id="lang">
      <option ${complaint.lang==='English'?'selected':''}>English</option>
      <option ${complaint.lang==='Hindi'?'selected':''}>Hindi</option>
    </select>
    <label>State *</label>
    <input id="state" value="${complaint.state||''}" placeholder="Select State">
    <label>City/Village</label>
    <input id="city" value="${complaint.city||''}" placeholder="City or Village name">
    <label>Complaint Description *</label>
    <textarea id="desc" placeholder="Describe your complaint in detail...">${complaint.desc||''}</textarea>
    <div class="checkbox-row"><input type="checkbox" id="agree" ${complaint.agree?'checked':''}> I agree to the terms and privacy policy.</div>
    <div class="checkbox-row"><input type="checkbox" id="accurate" ${complaint.accurate?'checked':''}> This complaint contains accurate information.</div>
    <div id="err" class="error"></div>
    <div class="form-btn-row">
      <button class="btn-outline" style="color:midnightblue;border:1px solid silver;" onclick="go('home')">← Back</button>
      <button class="btn-primary" style="background:midnightblue;color:white;" onclick="analyzeComplaint()">Analyze Complaint with AI →</button>
    </div>
  </div>
`;

function analyzeComplaint(){
  const name = document.getElementById('name').value.trim();
  const state = document.getElementById('state').value.trim();
  const desc = document.getElementById('desc').value.trim();
  const agree = document.getElementById('agree').checked;
  const accurate = document.getElementById('accurate').checked;
  if(!name || !state || !desc || !agree || !accurate){
    document.getElementById('err').textContent = 'Please fill required fields and confirm both checkboxes.';
    return;
  }
  complaint.name = name;
  complaint.lang = document.getElementById('lang').value;
  complaint.state = state;
  complaint.city = document.getElementById('city').value.trim();
  complaint.desc = desc;

  go('loading');

  setTimeout(() => {
    const guess = fakeCategoryFromText(desc);
    complaint.id = genComplaintId();
    complaint.detectedCategory = guess.cat;
    complaint.dept = guess.dept;
    complaint.location = (complaint.city ? complaint.city + ', ' : '') + complaint.state;
    const urgentWords = ['urgent','emergency','danger','accident','fire'];
    complaint.priority = urgentWords.some(w => desc.toLowerCase().includes(w)) ? 'HIGH' : 'MEDIUM';
    complaint.confidence = Math.floor(85 + Math.random()*13);
    complaint.summary = "The citizen has reported an issue related to " + guess.cat.toLowerCase() + " in " + complaint.location + ".";
    complaint.recommendedAction = `Forward complaint to ${guess.dept} for immediate inspection and action.`;
    go('analysis');
  }, 2500);
}

views.loading = () => `
  ${stepper(2)}
  <div class="form-card center">
    <div class="spinner"></div>
    <h2>Analyzing your complaint…</h2>
    <p class="muted">Our AI is detecting category, department and priority.</p>
  </div>
`;

views.analysis = () => `
  ${stepper(2)}
  <div class="form-card" style="max-width:600px;">
    <h2>AI Complaint Analysis</h2>
    <div class="result-grid">
      <div class="result-box"><div class="label">Complaint ID</div><div class="value">${complaint.id}</div></div>
      <div class="result-box"><div class="label">Detected Category</div><div class="value">${complaint.detectedCategory}</div></div>
      <div class="result-box"><div class="label">Recommended Department</div><div class="value">${complaint.dept}</div></div>
      <div class="result-box"><div class="label">Detected Location</div><div class="value">${complaint.location}</div></div>
      <div class="result-box"><div class="label">Priority</div><div class="value"><span class="badge ${complaint.priority}">${complaint.priority}</span></div></div>
      <div class="result-box"><div class="label">Confidence</div><div class="value">${complaint.confidence}%</div></div>
    </div>
    <div class="action-box"><b>AI Summary:</b> ${complaint.summary}</div>
    <div class="action-box"><b>Suggested Action:</b> ${complaint.recommendedAction}</div>
    <div class="form-btn-row">
      <button class="btn-outline" style="color:midnightblue;border:1px solid silver;" onclick="go('details')">← Edit Complaint</button>
      <button class="btn-primary" style="background:midnightblue;color:white;" onclick="submitComplaint()">Submit Complaint →</button>
    </div>
  </div>
`;

function submitComplaint(){
  complaint.submissionDate = new Date().toLocaleDateString('en-GB', {day:'2-digit', month:'long', year:'numeric'});
  go('confirmation');
}

views.confirmation = () => `
  ${stepper(3)}
  <div class="form-card center">
    <div class="checkmark">✓</div>
    <h2>Complaint Successfully Registered</h2>
    <p class="muted">Your complaint has been submitted to the concerned department.</p>
    <div class="receipt">
      <div class="row"><span>Complaint ID</span><b>${complaint.id}</b></div>
      <div class="row"><span>Submission Date</span><b>${complaint.submissionDate}</b></div>
      <div class="row"><span>Department</span><b>${complaint.dept}</b></div>
      <div class="row"><span>Priority</span><b>${complaint.priority}</b></div>
      <div class="row"><span>Expected Response</span><b>Within 7 working days</b></div>
    </div>
    <div class="form-btn-row">
      <button class="btn-primary" style="background:midnightblue;color:white;" onclick="go('track')">Track Complaint</button>
      <button class="btn-outline" style="color:midnightblue;border:1px solid silver;" onclick="resetAndFile()">File Another</button>
    </div>
  </div>
`;

function resetAndFile(){
  complaint = {};
  go('home');
}

views.track = () => `
  <div class="form-card center">
    <h2>Track Your Complaint</h2>
    <p class="muted">Enter your Complaint ID to check the current status.</p>
    <input id="trackId" placeholder="e.g. GRV-2026-24208">
    <button class="btn-primary" style="background:midnightblue;color:white;width:100%;margin-top:12px;" onclick="alert('This demo does not store complaints between visits.')">Track</button>
    <button class="btn-outline" style="color:midnightblue;border:1px solid silver;width:100%;margin-top:10px;" onclick="go('home')">← Home</button>
  </div>
`;

render();