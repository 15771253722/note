1. for循环

   ```java
   1.YzxxVOList.forEach(vo->{
   					vo.setZspmDm(SSGZGyUtil.isNull(SSGZGyCacheUtil.dm2Mc("DM_GY_ZSPM", vo.getZspmDm(), "ZSPMMC"))?"":SSGZGyCacheUtil.dm2Mc("DM_GY_ZSPM", vo.getZspmDm(), "ZSPMMC"));
   				});
   2.for(ZSYzxxVO vo:YzxxVOList){
   					vo.setZspmDm(SSGZGyUtil.isNull(SSGZGyCacheUtil.dm2Mc("DM_GY_ZSPM", vo.getZspmDm(), "ZSPMMC"))?"":SSGZGyCacheUtil.dm2Mc("DM_GY_ZSPM", vo.getZspmDm(), "ZSPMMC"));
   					vo.setZsxmDm(SSGZGyUtil.isNull(SSGZGyCacheUtil.dm2Mc("DM_GY_ZSXM", vo.getZsxmDm(), "ZSXMMC"))?"":SSGZGyCacheUtil.dm2Mc("DM_GY_ZSXM", vo.getZsxmDm(), "ZSXMMC"));
   					if("20".equals(vo.getSkzlDm())){
   						znjhj = SwordMathUtils.add(znjhj+vo.getYbtse());

   					}
   ```

   ​

2. ​