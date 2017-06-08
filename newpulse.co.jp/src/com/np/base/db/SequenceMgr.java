package com.np.base.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.np.base.orm.ModelMapper;
import com.np.base.orm.SQLBuilder;
import com.np.base.reflect.PojoUtils;
import com.np.order.models.SequenceCtrl;

public class SequenceMgr {
	private static Log log = LogFactory.getLog(SequenceMgr.class);

	@SuppressWarnings("rawtypes")
	public static long nextSeq(Class type) throws Exception {
		return nextSeq(type.getSimpleName());
	}

	public static long nextSeq(final String type) throws Exception {
		final SequenceCtrl seqCtrl = new SequenceCtrl();
		seqCtrl.setSeqId(type);
		seqCtrl.setSeqType("entity");

		final long[] seq = new long[1];
		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				ModelMapper mapper = PojoUtils.getMapper(SequenceCtrl.class);

				String sql = SQLBuilder.createSelectSQL(seqCtrl);
				sql += " FOR UPDATE";
				PreparedStatement stmt = con.prepareStatement(sql);
				log.debug(sql);

				int index = 1;
				for (String col : mapper.getColumns()) {
					Object value = mapper.getValue(seqCtrl, mapper.toPropertyName(col));
					if (value == null)
						continue;
					stmt.setObject(index++, value);
				}

				ResultSet rs = stmt.executeQuery();
				SequenceCtrl sc;
				if (rs.next()) {
					sc = (SequenceCtrl) mapper.toModel(rs);
					long seq = sc.getCurrentValue();
					seq++;
					sc.setCurrentValue(seq);
					TxMgr.update(con, sc);
				} else {
					seqCtrl.setMinValue(1L);
					seqCtrl.setMaxValue(Long.MAX_VALUE);
					seqCtrl.setCurrentValue(1L);
					TxMgr.insert(con, seqCtrl);
					sc = seqCtrl;
				}
				rs.close();
				seq[0] = sc.getCurrentValue();

			}

		});

		return seq[0];
	}

	public static long currentSeq(final String type) throws Exception {
		final SequenceCtrl seqCtrl = new SequenceCtrl();
		seqCtrl.setSeqId(type);
		seqCtrl.setSeqType("entity");

		final long[] seq = new long[1];
		TxMgr.update(new ITxProc() {

			@Override
			public void process(Connection con) throws Exception {
				ModelMapper mapper = PojoUtils.getMapper(SequenceCtrl.class);

				String sql = SQLBuilder.createSelectSQL(seqCtrl);
				// sql += " FOR UPDATE";
				PreparedStatement stmt = con.prepareStatement(sql);
				log.debug(sql);

				int index = 1;
				for (String col : mapper.getColumns()) {
					Object value = mapper.getValue(seqCtrl, mapper.toPropertyName(col));
					if (value == null)
						continue;
					stmt.setObject(index++, value);
				}

				ResultSet rs = stmt.executeQuery();
				SequenceCtrl sc;
				if (rs.next()) {
					sc = (SequenceCtrl) mapper.toModel(rs);
					long seq = sc.getCurrentValue();
					sc.setCurrentValue(seq);
				} else {
					seqCtrl.setMinValue(1L);
					seqCtrl.setMaxValue(Long.MAX_VALUE);
					seqCtrl.setCurrentValue(1L);
					sc = seqCtrl;
				}
				rs.close();
				seq[0] = sc.getCurrentValue();

			}

		});

		return seq[0];
	}

}
