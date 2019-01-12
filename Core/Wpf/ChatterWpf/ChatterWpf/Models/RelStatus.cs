using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ChatterWpf.Models
{
    [Flags]
    public enum RelStatus
    {
        NONE=0,
        FRIENDSHIP_PENDING=1<<0,
        FRIEND=1 << 1,
        BLOCKED=1<<2,
    }
}
