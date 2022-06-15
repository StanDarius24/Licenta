namespace Interpreter.Models.metrics {
    public class MetricsModel
    {
        public MetricsModel(string s, string path1)
        {
            name = s;
            path = path1;
        }
        public string name { set; get; }
        public string path { set; get; }
        public float AMW { set; get; }
        public float WMC { set; get; }
        public float NOM { set; get; }
        public float NOPA { set; get; }
        public float NProtM { set; get; }
        public float ATFD { set; get; }
        public float ATFD2 { set; get; }
        public float FDP { set; get; }
        public float WOC { set; get; }
        public float BOvR { set; get; }
        public float CC { set; get; }
        public float CM { set; get; }
        public float CINT { set; get; }
        public float CDISP { set; get; }
        public float BUR { set; get; }
        public float HIT { set; get; }
        public float DIT { set; get; }
        public float NOC { set; get; }
        public float RFC { set; get; }
    }
}