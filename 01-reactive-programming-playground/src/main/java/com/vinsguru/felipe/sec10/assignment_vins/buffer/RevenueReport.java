package com.vinsguru.felipe.sec10.assignment_vins.buffer;

import java.time.LocalTime;
import java.util.Map;

public record RevenueReport(LocalTime time, Map<String, Integer> revenue) {
}
